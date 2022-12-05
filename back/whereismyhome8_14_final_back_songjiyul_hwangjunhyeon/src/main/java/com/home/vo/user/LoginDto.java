package com.home.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "LoginDto", description = "로그인 정보")
public class LoginDto {
	@ApiModelProperty("고객 아이디")
	private String username;
	
	@ApiModelProperty("고객 비밀번호")
	private String password;
}
