package com.home.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.service.fav.FavoriteService;
import com.home.vo.user.FavoriteLoc;
import com.home.vo.user.LoginDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/fav")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value = "Favorite Controller", description = "관심지역 컨트롤러")
public class FavoriteController {
	
	@Autowired
	private final FavoriteService favService;
	
	@GetMapping("/{username}/{dongCode}")
	@ApiOperation(value = "checkFavorite", notes = "현재 로그인 한 유저가 해당 지역을 관심 지역으로 등록했는지 판단\nURI=/fav/username/dongCode\n example) /fav/test/1111015700")
	@ApiResponses({
		@ApiResponse(code = 200, message = "해당 유저가 해당 지역 관심있음"),
		@ApiResponse(code = 204, message = "해당 유저가 해당 지역 관심없음"),
		@ApiResponse(code = 404, message = "페이지 오류"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Integer> checkFav(@PathVariable("username") String username, @PathVariable("dongCode") String dongCode) throws Exception {
		// 확인을 위해 세션에 직접 user 정보 담기.
//		LoginDto temp = new LoginDto();
//		temp.setId("ssafy");
//		temp.setPassword("1234");
//		session.setAttribute("userinfo", temp);
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("dongCode", dongCode);
		System.out.println(map);
		FavoriteLoc favorite = favService.checkFavorite(map);
		System.out.println(favorite);
		if(favorite!=null) {
			return new ResponseEntity<Integer>(HttpStatus.OK.value(), HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT);
	}
	@GetMapping("/userFav")
	@ApiOperation(value = "selectAllFav", notes = "현재 로그인한 유저가 관심있는 지역 리스트 불러오기\nURI=/fav/userFav")
	@ApiResponses({
		@ApiResponse(code = 200, message = "유저가 관심있는 지역 불러오기"),
		@ApiResponse(code = 204, message = "유저가 관심있는 지역 없음"),
		@ApiResponse(code = 404, message = "페이지 오류"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<FavoriteLoc>> selectAllFav(HttpSession session) throws Exception{
		// 임시로 확인하기 위해 세션에 임의 등록
//		LoginDto temp = new LoginDto();
//		temp.setId("ssafy");
//		temp.setPassword("1234");
//		session.setAttribute("userinfo", temp);
		LoginDto auth = (LoginDto) session.getAttribute("userinfo");
		List<FavoriteLoc> list = favService.selectAllFav(auth.getUsername());
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<FavoriteLoc>>(list, HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/loc")
	@ApiOperation(value = "addFavorite", notes = "현재 로그인한 유저 - 관심 지역 추가\nURI=/fav/loc\n example) /fav/loc\n로그인 되어 있는 상태로 해당 지역 추가")
	@ApiResponses({
		@ApiResponse(code = 200, message = "유저 관심 지역 추가"),
		@ApiResponse(code = 404, message = "페이지 오류"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Integer> addFav(@RequestBody FavoriteLoc favoriteLoc, HttpServletRequest request) throws Exception {
		String jwt = "";
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			jwt = bearerToken.substring(7);
		}
		System.out.println("ADD FAV => JWT 토큰 : " + jwt);
		// 임시로 확인하기 위해 세션에 임의 등록
//		LoginDto temp = new LoginDto();
//		temp.setId("ssafy");
//		temp.setPassword("1234");
		System.out.println(favoriteLoc);
		int result = favService.addFavorite(favoriteLoc);
		if(result!=0) {
			System.out.println(result);
			return new ResponseEntity<Integer>(HttpStatus.OK.value(), HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/loc/{username}/{dongCode}")
	@ApiOperation(value = "deleteFavorite", notes = "현재 로그인한 유저 - 관심 지역 제거\\nURI=/fav/username/dongCode\\n example) /fav/test/1111015700\"\\n로그인 되어 있는 상태로 해당 지역 제거")
	@ApiResponses({
		@ApiResponse(code = 200, message = "유저 관심 지역 제거"),
		@ApiResponse(code = 404, message = "페이지 오류"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Integer> deleteFav(@PathVariable("username") String username, @PathVariable("dongCode") String dongCode) throws Exception {
		// 임시로 확인하기 위해 세션에 임의 등록
//		LoginDto temp = new LoginDto();
//		temp.setId("ssafy");
//		temp.setPassword("1234");
//		session.setAttribute("userinfo", temp);
		System.out.println("DELETE : "+ username + " " + dongCode);
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("auth", favoriteLoc.getId());
//		map.put("dongCode", favoriteLoc.getDongCode());
//		System.out.println(map);
		int result = favService.deleteFavorite(new FavoriteLoc(username, dongCode));
		if(result!=0) {
			return new ResponseEntity<Integer>(HttpStatus.OK.value(), HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
	}
}
