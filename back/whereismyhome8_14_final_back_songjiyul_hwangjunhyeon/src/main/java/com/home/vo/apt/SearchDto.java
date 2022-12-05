package com.home.vo.apt;

import java.math.BigInteger;

import lombok.Data;

@Data
public class SearchDto {
	// 거래 번호
	private BigInteger no;
	// 아파트 코드
	private BigInteger aptCode;
	// 아파트 이름
	private String apartmentName;
	// 건축 연도
	private int buildYear;
	// 도로명
	private String roadName;
	// 지번
	private String jibun;
	// 위도 경도
	private String lng;
	private String lat;
	// 면접
	private String area;
	// 층
	private String floor;
	// 거래 금액
	private String dealAmount;
	// 동
	private String dongName;
	// 동 코드
	private String dongCode;
	// 거래 일자
	private String dealYear;
	private String dealMonth;
	private String dealDay;
}
