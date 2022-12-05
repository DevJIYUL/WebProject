package com.home.vo.user;

import java.util.List;  

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "User", description = "사용자 정보")
public class SignupDto {
	@ApiModelProperty("고객 아이디")
	private String username;
	
	@ApiModelProperty("고객 비밀번호")
	private String password;
	
	@ApiModelProperty("고객 이메일")
	private String email;
	
	@ApiModelProperty("이메일 도메인")
	private String domain;
	
	@ApiModelProperty("권한")
	private String role;
	
	@ApiModelProperty("가입날짜")
	private String joinDate;
	
	@ApiModelProperty("거주 동")
	private String residence;
	
	@ApiModelProperty("관심 키워드")
	private List<String> favoritecateDTOs;
}
