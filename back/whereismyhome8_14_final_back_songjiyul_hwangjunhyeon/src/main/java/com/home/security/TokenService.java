package com.home.security;

import java.util.Optional;

import com.home.vo.token.Token;


//import org.springframework.beans.factory.annotation.Value;

public interface TokenService {
//	@Value("${}")
	Optional<Token> findByUsername(String username);
	Optional<Token> findByRefreshToken(String token);
	
	int insertToken(Token token);
	// 토큰 제거 - refresh token 만료 and db에 저장된 access token 이 아직 유효하다면(해커해킹)
	int deleteByUsername(String username);
	int updateTokenByUsername(Token token);
	int updateAccessTokenByUsername(String username, String token);
}
