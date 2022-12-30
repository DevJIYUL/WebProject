package com.home.vo.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Favorite", description = "관심지역")
public class FavoriteLoc {
	private String username;
	private String dongCode;
	private String sidoName;
	
	private String gugunName;
	
	private String dongName;

	public FavoriteLoc(String username, String dongCode) {
		super();
		this.username = username;
		this.dongCode = dongCode;
	}
}
