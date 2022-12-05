package com.home.vo.apt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Dongcode", description = "전국 시,도, 구군 정보")
public class DongDto {
	@ApiModelProperty("지역코드")
	private String dongCode;
	@ApiModelProperty("시,도 이름")
	private String sidoName;
	@ApiModelProperty("구군 이름")
	private String gugunName;
	@ApiModelProperty("동 이름")
	private String dongName;

}
