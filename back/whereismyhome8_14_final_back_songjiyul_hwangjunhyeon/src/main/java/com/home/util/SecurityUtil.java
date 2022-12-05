package com.home.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

	private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
	
	private SecurityUtil() {
		
	}
	
	public static Optional<String> getCurrentUsername() {
		// request 들어와서 jwt 토큰 유효하면 authentication 객체 저장된다.
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// 현재 유저 없을 경우
		if(authentication == null) {
			logger.debug("Security Context 에 인증 정보 없습니다.");
			 return Optional.empty();
		}
		
		// 
		String username = null;
		if(authentication.getPrincipal() instanceof UserDetails) {
			UserDetails securityUer = (UserDetails) authentication.getPrincipal();
			username = securityUer.getUsername();
		} else if (authentication.getPrincipal() instanceof String) {
			username = (String) authentication.getPrincipal();
		}
		
		return Optional.ofNullable(username);
	}
}