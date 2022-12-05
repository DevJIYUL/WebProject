package com.home.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;  

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.service.user.UserService;
import com.home.vo.user.FavoriteLoc;
import com.home.vo.user.LoginDto;
import com.home.vo.user.SignupDto;
import com.home.vo.user.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
@CrossOrigin("*")
@RestController
@Api(value = "UserController", description = "유저 컨트롤러")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/userAll")
	@ApiOperation(value = "userAll", notes = "전체조회")
	@ApiResponses({
		@ApiResponse(code=200, message ="succes" ),
		@ApiResponse(code=202, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<List<User>> userall() throws Exception{
		List<User> list = userService.listUser();
		System.out.println(list.toString());
		if(list != null) return new ResponseEntity<List<User>>(list,HttpStatus.OK);
		else return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	}
	
//	@PostMapping("/user")
//	@ApiOperation(value = "joinuser", notes = "회원가입")
//	@ApiResponses({
//		@ApiResponse(code=200, message ="success" ),
//		@ApiResponse(code=202, message ="no_content" ),
//		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
//		@ApiResponse(code=500, message ="서버 에러" ),
//	})
//	public ResponseEntity<?> joinUser(@RequestBody SignupDto signupdto) throws Exception{
//		System.out.println(signupdto.toString());
//		
//		int x = userService.joinUser(signupdto);
//		if(x==1) return new ResponseEntity<Integer>(HttpStatus.OK.value(),HttpStatus.OK);
//		else return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
//	}
//	
//	
//	@PostMapping("/login")
//	@ApiOperation(value = "loginUser", notes = "로그인")
//	@ApiResponses({
//		@ApiResponse(code=200, message ="success" ),
//		@ApiResponse(code=202, message ="no_content" ),
//		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
//		@ApiResponse(code=500, message ="서버 에러" ),
//	})
//	public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto,HttpServletRequest request) throws Exception{
//		int x = userService.loginUser(loginDto);
//		if(x==1) {
//			request.getSession().setAttribute("userinfo", loginDto);
//			return new ResponseEntity<Integer>(HttpStatus.OK.value(),HttpStatus.OK);
//		}
//		else return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
//	}
	
	
	@GetMapping("/auth/user/{username}")
	@ApiOperation(value = "userOne", notes = "유저 id로 유저 찾기")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=202, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> getUser(@PathVariable("username") String username,HttpServletRequest request) throws Exception{
		User user = userService.getUser(username);
		System.out.println(user);
		System.out.println(request.getRemoteAddr());
		user.setIp(request.getRemoteAddr());
		System.out.println(user);
		if(user != null) {
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		else return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	@PutMapping("/user")
	@ApiOperation(value = "userEdit", notes = "유저 정보 변경")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=202, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> getUser(@RequestBody User user) throws Exception{
		int x = userService.updateUser(user);
		if(x == 1) {
			return new ResponseEntity<Integer>(HttpStatus.OK.value(),HttpStatus.OK);
		}
		else return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
	}
	@GetMapping("/userpassword/{username}")
	@ApiOperation(value = "userOne", notes = "유저 id로 유저 찾기")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=202, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> findPassword(@PathVariable("username") String username) throws Exception{
		User user = userService.getUser(username);
		if(user != null) {
			return new ResponseEntity<String>(user.getPassword(),HttpStatus.OK);
		}
		else return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	@GetMapping("/logout")
	public void logout(HttpServletRequest request) throws Exception{
		if(request.getSession() != null) request.getSession().removeAttribute("userinfo");
	}
	
	@GetMapping("/auth/userdong/{username}")
	@ApiOperation(value = "userOne", notes = "유저 id로 관심 지역")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=202, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> getUserDong(@PathVariable("username") String username) throws Exception{
		List<FavoriteLoc> userdong = userService.getUserDong(username);
		System.out.println(userdong);
		if(userdong != null) {
			return new ResponseEntity<List<FavoriteLoc>>(userdong,HttpStatus.OK);
		}
		else return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	
}
