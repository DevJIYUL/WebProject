package com.home.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.home.mapper.TokenMapper;
import com.home.vo.token.Token;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	private final TokenMapper tokenMapper;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Token> findByUsername(String username) {
		return tokenMapper.findByUsername(username);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Token> findByRefreshToken(String token) {
		return tokenMapper.findByRefreshToken(token);
	}

	@Override
	@Transactional
	public int insertToken(Token token) {
		return tokenMapper.insertToken(token);
	}

	@Override
	@Transactional
	public int deleteByUsername(String username) {
		return tokenMapper.deleteByUsername(username);
	}


	@Override
	@Transactional
	public int updateTokenByUsername(Token token) {
		return tokenMapper.updateTokenByUsername(token);
	}
	
	@Override
	@Transactional
	public int updateAccessTokenByUsername(String username, String token) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", String.valueOf(username));
		map.put("token", token);
		return tokenMapper.updateAccessTokenByUsername(map);
	}
}