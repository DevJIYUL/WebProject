package com.home.security.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;


@Component
public class TokenProvider implements InitializingBean{
	private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
	
	private static final String AUTHORITIES_KEY = "auth";
	
	private final String secret;
	private final long accesstokenValidityInMilliSeconds;
	private final long refreshtokenValidityInMilliSeconds;

	private Key key;
	
	public TokenProvider(
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.accesstoekn-validity-in-seconds}") long accesstokenValidityInMilliSeconds,
		@Value("${jwt.refreshtoekn-validity-in-seconds}") long refreshtokenValidityInMilliSeconds
	) {
		this.secret = secret;
		this.accesstokenValidityInMilliSeconds = accesstokenValidityInMilliSeconds * 1000;
		this.refreshtokenValidityInMilliSeconds = refreshtokenValidityInMilliSeconds * 1000;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	} 

	
	public String createAccessToken(Authentication authentication) {
		// Authority
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		System.out.println(authorities);
		
		// set expiration time
		long now = (new Date()).getTime();
		Date validity = new Date(now + this.accesstokenValidityInMilliSeconds);
		
		return Jwts.builder()
				.setSubject(authentication.getName()) // username
				// claim??? key="auth" , data = username ??? ????????? ??????
				.claim(AUTHORITIES_KEY, authorities) // Key, roles
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity)
				.compact();
	}
	public String createRefreshToken(Authentication authentication) {
		// Authority
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		System.out.println(authorities);
		
		// set expiration time
		long now = (new Date()).getTime();
		Date validity = new Date(now + this.refreshtokenValidityInMilliSeconds);
		
		return Jwts.builder()
				.setSubject(authentication.getName()) // username
				// claim??? key="auth" , data = username ??? ????????? ??????
				.claim(AUTHORITIES_KEY, authorities) // Key, roles
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity)
				.compact();
	}
	
	// Token ????????? ????????? Authentication ?????? ??????
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		// UserDetail ?????? -> username, password(????????? ??????)
		User principal = new User(claims.getSubject(), "", authorities);
		// Authentication ??????
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}
	
	// Token ????????? ??????
	public boolean validateToken(String token) {
		try {
			// ?????? ???????????? ????????? ??????
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch(SecurityException | MalformedJwtException e) {
			logger.info("????????? JWT ???????????????.");
//			throw new AuthenticationException("????????? JWT ???????????????");
		} catch(ExpiredJwtException e ) {
			logger.info("????????? ???????????????.");
//			throw new AuthenticationException("????????? JWT ???????????????");
		} catch(UnsupportedJwtException e) {
			logger.info("???????????? ?????? JWT ???????????????.");
//			throw new AuthenticationException("????????? JWT ???????????????");
		} catch(IllegalArgumentException e) {
			logger.info("????????? JWT ???????????????.");
//			throw new AuthenticationException("????????? JWT ???????????????");
		} 
		return false;
	}
}