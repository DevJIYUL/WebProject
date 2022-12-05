package com.home.vo.token;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
	/*
	 * Refresh Token -> DB 저장 필요
	 * 
	 * id : AI
	 * user의 username (FK)
	 * String token
	 * expirationDate
	 * 
	 * */
	private String username;
	private String token;
	private Date expirationDate;
}
