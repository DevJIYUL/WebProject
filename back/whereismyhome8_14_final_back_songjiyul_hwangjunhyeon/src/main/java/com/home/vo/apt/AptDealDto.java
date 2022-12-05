package com.home.vo.apt;

import java.math.BigInteger;

import lombok.Data;

@Data
public class AptDealDto {
	private BigInteger no;
	private BigInteger aptCode;
	private String dealAmount;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String area;
	private String floor;
	private String cancelDealType;
	private String rentMoney;
}
