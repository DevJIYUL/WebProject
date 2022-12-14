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
	@ApiOperation(value = "authenticate", notes="?????????")
	public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
		System.out.println(loginDto);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		// authenticate ????????? ??? loadUserByUsername ??? ????????????.
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// Context??? ??????
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String accessToken = tokenProvider.createAccessToken(authentication);
		String refreshToken = tokenProvider.createRefreshToken(authentication);

		// token db??? ??????
		Token token = tokenService.findByUsername(loginDto.getUsername()).orElse(null);
		System.out.println("Login Token -> DB ?????? " + token);
		Token newToken = new Token(loginDto.getUsername(), accessToken, refreshToken);
		if (token == null) {
			// ?????? ???????????? db??? username ???????????? ?????? ????????? ????????? ??????
			tokenService.insertToken(newToken);
		} else {
			// ?????? ?????? ?????? ?????? ??????????????? ?????? ??????
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
			System.out.println("?????? ???????????? ?????????");
			return ResponseEntity.badRequest().body(new ErrorMessage("?????? ????????? ??????????????????"));
		}

		Map<String, String> email = new HashMap<String, String>();
		email.put("email", signupDto.getEmail());
		email.put("domain", signupDto.getDomain());

		if (userService.existsByEmail(email)) {
			System.out.println("?????? ???????????? ?????????");
			return ResponseEntity.badRequest().body(new ErrorMessage("?????? ????????? ??????????????????"));
		}

		int result = userService.save(signupDto);
		if (result == 1) {
			System.out.println("???????????? ??????");
			return ResponseEntity.ok("??????????????? ??????????????????.");
		}
		return ResponseEntity.badRequest().body(new ErrorMessage("??????????????? ??????????????????"));
	}

	// 1. Header ??? refreshtoken ????????? ????????????
	// 2. Body ??? ????????? ????????? ??????.
	@PostMapping("/auth/refreshtoken")
	public ResponseEntity<?> refreshToken(HttpServletRequest request) throws AuthenticationException {
		
		
		// 1. HttpServletRequest ??????
		String jwt = "";
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			jwt = bearerToken.substring(7);
		}
		System.out.println(jwt);
		/*
		 * 1. DB?????? Token ????????????. => id, accessToken, refreshToken 2. refreshToken ???????????? ?????????
		 * -> token delete 3. accesstoken??? ??????????????? -> refreshToken(??????) ?????? (????????????) [ token
		 * ?????? ] 4. accestoken ???????????? ?????? refreshtoken ??????????????? -> accesstoken ????????? -> db???
		 * accesstoken ??????
		 */
		// 1.
		Token token = tokenService.findByRefreshToken(jwt)
				.orElseThrow(() -> new IllegalArgumentException("????????? DB?????? ?????? ??? ????????????."));

		String username = token.getUsername();
		String accessToken = token.getAccessToken();
		String refreshToken = token.getRefreshToken();

		// 2. refresh Token InValid
		if (!tokenProvider.validateToken(refreshToken)) {
			tokenService.deleteByUsername(username);
			// 403 Error
			throw new AuthenticationException("????????? ???????????????.");
		} else {
			// 3. Hacked RefreshToken
			if (tokenProvider.validateToken(accessToken)) {
				tokenService.deleteByUsername(username);
				// 403 Error
				throw new AuthenticationException("????????? ???????????????.");
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
			throw new AuthenticationException("????????? ???????????????.");
		}
	}

	// logout ?????? ?????? -> refreshToken?????? ???????????? Token Entity??? username, tk1, tk2 ??? ????????????
	// username?????? ??????
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
		return ResponseEntity.ok("????????????");
	}
}
