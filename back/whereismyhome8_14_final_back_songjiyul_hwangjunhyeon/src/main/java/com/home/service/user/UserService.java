package com.home.service.user;

import java.util.List;
import java.util.Map;

import com.home.vo.user.FavoriteLoc;
import com.home.vo.user.LoginDto;
import com.home.vo.user.SignupDto;
import com.home.vo.user.User;


public interface UserService {
	// 회원가입
	int save(SignupDto signupDto) throws Exception;
	
	Boolean existsByUsername(String username)throws Exception;
	Boolean existsByEmail(Map<String, String> email)throws Exception;


	/* Admin */
	// user 다 뿌려주기
	List<User> listUser() throws Exception;
	// 회원 정보 변경할때 불러올것
	User getUser(String username) throws Exception;
	// 회원 정보 변경
	int updateUser(User user) throws Exception;
	// 회원 삭제 할때
	int findPassword(String username) throws Exception;

	List<FavoriteLoc> getUserDong(String username);
	
}
