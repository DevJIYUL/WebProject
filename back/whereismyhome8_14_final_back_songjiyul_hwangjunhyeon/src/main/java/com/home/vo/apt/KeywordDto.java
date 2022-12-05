package com.home.vo.apt;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeywordDto {
	private BigInteger no;
	private BigInteger aptCode;
	private String dongCode;
	private String year;
	private String month;
}
