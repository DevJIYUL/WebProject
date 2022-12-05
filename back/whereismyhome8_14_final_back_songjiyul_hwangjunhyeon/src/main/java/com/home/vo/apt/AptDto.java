package com.home.vo.apt;

import java.math.BigInteger;

import lombok.Data;

@Data
public class AptDto {
	private BigInteger aptCode;
	private String aptName;
	private String dongCode;
	private String dongName;
	private int buildYear;
	private String jibun;
	private String lat;
	private String lng;
	private String img;
}
