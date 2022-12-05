package com.home.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.home.mapper.UserMapper;
import com.home.vo.user.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserMapper userMapper;
	
	public UserDetailsServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	// 로그인 시 DB에서 유저정보와 권한정보를 함께 가져오는 기능 수행
	// DB에서 가져온 정보를 기준으로 유저가 활성화 상태라면 유저의 
	/// 권한정보, username, password 를 가진 User[Security] 객체를 return 한다. 
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		User user = userMapper.findOneByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException(username + " -> DB에서 찾을 수 없습니다."));
		System.out.println(user);
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole()); 
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(authority);
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(new SimpleGrantedAuthority(user.getRole()));
//		List<GrantedAuthority> authorities = user.getAuthorities().stream()
//				.map(role -> new SimpleGrantedAuthority(role.getAuthorityName()))
//				.collect(Collectors.toList());
		System.out.println(authorities);
		// UserDetail 리턴
		return new org.springframework.security.core.userdetails.
				User(user.getUsername(), user.getPassword(), authorities);
	}
	
	

}
