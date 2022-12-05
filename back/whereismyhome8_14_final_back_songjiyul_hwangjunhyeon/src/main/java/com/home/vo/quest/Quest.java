package com.home.vo.quest;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Quest", description = "공지사항")
public class Quest {
	@ApiModelProperty("번호")
	private int id;
	@ApiModelProperty("글쓴이")
	private String username;
	@ApiModelProperty("제목")
	private String title;
	@ApiModelProperty("내용")
	private String content;
	@ApiModelProperty("비밀글")
	private boolean privateFlag;
	@ApiModelProperty("답장")
	private boolean answeredFlag;
	@ApiModelProperty("작성일")
	private String regDate;
	@ApiModelProperty("댓글")
	private String answer;
}
