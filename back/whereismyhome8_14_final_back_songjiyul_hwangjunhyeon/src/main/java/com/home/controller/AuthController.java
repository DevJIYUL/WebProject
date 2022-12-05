package com.home.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.security.TokenService;
import com.home.security.jwt.TokenProvider;
import com.home.service.user.UserService;
import com.home.vo.msg.ErrorMessage;
import com.home.vo.token.JwtResponse;
import com.home.vo.token.Token;
import com.home.vo.user.LoginDto;
import com.home.vo.user.SignupDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@CrossOrigin("*")
@Api(value = "Auth Controller", description = "Spring Security")
public class AuthController {

	private final TokenProvider tokenProvider;
	private final TokenService tokenService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthController(TokenProvider tokenProvider, TokenService tokenService,
			AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService,
			PasswordEncoder passwordEncoder) {
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.tokenService = tokenService;
		this.tokenProvider = tokenProvider;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	private static final String AUTHORIZATION_HEADER = "Authorization";

	@PostMapping("/api/authenticate")
	@ApiOperation(value = "authenticate", notes="로그인")
	public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
		System.out.println(loginDto);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		// authenticate 실행될 때 loadUserByUsername 이 실행된다.
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// Context에 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String accessToken = tokenProvider.createAccessToken(authentication);
		String refreshToken = tokenProvider.createRefreshToken(authentication);

		// token db에 저장
		Token token = tokenService.findByUsername(loginDto.getUsername()).orElse(null);
		System.out.println("Login Token -> DB 확인 " + token);
		Token newToken = new Token(loginDto.getUsername(), accessToken, refreshToken);
		if (token == null) {
			// 최초 로그인시 db에 username 기준으로 토큰 관리할 레코드 생성
			tokenService.insertToken(newToken);
		} else {
			// 토큰 만료 이후 다시 로그인하여 토큰 발급
			tokenService.updateTokenByUsername(newToken);
		}

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		System.out.println(userDetails);
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken, userDetails.getUsername(), roles));
	}

//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto) throws Exception {
		System.out.println(signupDto);
		if (userService.existsByUsername(signupDto.getUsername())) {
			System.out.println("이미 존재하는 아이디");
			return ResponseEntity.badRequest().body(new ErrorMessage("이미 가입된 사용자입니다"));
		}

		Map<String, String> email = new HashMap<String, String>();
		email.put("email", signupDto.getEmail());
		email.put("domain", signupDto.getDomain());

		if (userService.existsByEmail(email)) {
			System.out.println("이미 존재하는 이메일");
			return ResponseEntity.badRequest().body(new ErrorMessage("이미 가입된 사용자입니다"));
		}

		int result = userService.save(signupDto);
		if (result == 1) {
			System.out.println("회원가입 성공");
			return ResponseEntity.ok("회원가입에 성공했습니다.");
		}
		return ResponseEntity.badRequest().body(new ErrorMessage("회원가입에 실패했습니다"));
	}

	// 1. Header 로 refreshtoken 담아서 보내주자
	// 2. Body 에 담아서 보내도 된다.
	@PostMapping("/auth/refreshtoken")
	public ResponseEntity<?> refreshToken(HttpServletRequest request) throws AuthenticationException {
		
		
		// 1. HttpServletRequest 필요
		String jwt = "";
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			jwt = bearerToken.substring(7);
		}
		System.out.println(jwt);
		/*
		 * 1. DB에서 Token 가져온다. => id, accessToken, refreshToken 2. refreshToken 유효하지 않다면
		 * -> token delete 3. accesstoken이 유효하다면 -> refreshToken(유효) 탈취 (해킹당함) [ token
		 * 삭제 ] 4. accestoken 유효하지 않고 refreshtoken 유효하다면 -> accesstoken 재발급 -> db에
		 * accesstoken 갱신
		 */
		// 1.
		Token token = tokenService.findByRefreshToken(jwt)
				.orElseThrow(() -> new IllegalArgumentException("토큰을 DB에서 찾을 수 없습니다."));

		String username = token.getUsername();
		String accessToken = token.getAccessToken();
		String refreshToken = token.getRefreshToken();

		// 2. refresh Token InValid
		if (!tokenProvider.validateToken(refreshToken)) {
			tokenService.deleteByUsername(username);
			// 403 Error
			throw new AuthenticationException("만료된 토큰입니다.");
		} else {
			// 3. Hacked RefreshToken
			if (tokenProvider.validateToken(accessToken)) {
				tokenService.deleteByUsername(username);
				// 403 Error
				throw new AuthenticationException("만료된 토큰입니다.");
			} else {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String newAccessToken = tokenProvider
						.createAccessToken(authentication);
				tokenService.updateAccessTokenByUsername(username, newAccessToken);
				
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				System.out.println(userDetails);
				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());
				return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken, userDetails.getUsername(), roles));
			}
		}
	}

	@PostMapping("/auth/maintain")
	public ResponseEntity<?> maintainUser(HttpServletRequest request) throws AuthenticationException {
		String jwt = "";
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			jwt = bearerToken.substring(7);
		}
		if(tokenProvider.validateToken(jwt)) {
			Authentication authentication = tokenProvider.getAuthentication(jwt);
			String newAccessToken = tokenProvider.createAccessToken(authentication);
			return ResponseEntity.ok(new JwtResponse(newAccessToken, jwt));
		} else {
			throw new AuthenticationException("만료된 토큰입니다.");
		}
	}

	// logout 고민 필요 -> refreshToken으로 제거할지 Token Entity를 username, tk1, tk2 로 수정해서
	// username으로 할지
	@GetMapping("/api/logout") 
	public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
//		String jwt = "";
//		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//			jwt = bearerToken.substring(7);
//		}
//		Authentication authentication = tokenProvider.getAuthentication(jwt);
//		System.out.println(authentication);
//		if (authentication != null) {
			SecurityContextHolder.clearContext();
//			new SecurityContextLogoutHandler().logout(request, response, authentication);
//		}
		return ResponseEntity.ok("로그아웃");
	}
}
