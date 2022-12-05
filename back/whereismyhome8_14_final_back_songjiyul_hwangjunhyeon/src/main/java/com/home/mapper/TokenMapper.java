package com.home.mapper;

import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.home.vo.token.Token;


@Mapper
public interface TokenMapper {
	
	Optional<Token> findByUsername(String username);
	Optional<Token> findByRefreshToken(String token);

	int insertToken(Token token);
	
	int deleteByUsername(String username);
	int updateTokenByUsername(Token token);
	int updateAccessTokenByUsername(Map<String, String> map);
}
