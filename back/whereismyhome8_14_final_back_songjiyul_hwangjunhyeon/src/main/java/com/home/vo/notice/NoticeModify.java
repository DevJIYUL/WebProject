package com.home.vo.notice;

import java.util.ArrayList;
import java.util.List; 

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Notice", description = "공지사항")
public class NoticeModify {
	@ApiModelProperty("번호")
	private int noticeNo;
	@ApiModelProperty("제목")
	private String title;
	@ApiModelProperty("내용")
	private String content;
	@ApiModelProperty("작성일")
	private String wdate;
	@ApiModelProperty("조회수")
	private String hit;
	@ApiModelProperty("첨부파일")
	private ArrayList<String> deleteInfos;
	@ApiModelProperty("첨부파일")
	private List<FileInfoDto> addinfo;

	
}
