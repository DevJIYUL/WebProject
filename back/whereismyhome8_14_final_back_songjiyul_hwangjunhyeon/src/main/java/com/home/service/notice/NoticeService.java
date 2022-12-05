package com.home.service.notice;

import java.util.List; 

import com.home.vo.notice.Notice;
import com.home.vo.notice.NoticeModify;

public interface NoticeService {
	// 공지사항 등록
	int registerNotice(Notice notice) throws Exception;
	// 편집
	int editNotice(NoticeModify noticeModify) throws Exception;
	// 공지사항 보기
	List<Notice> listNotice() throws Exception;
	// 공지사항 상세보기
	Notice detailNotice(String id) throws Exception;
	// 상세보기 할 때 조회수 업
	void updateHit(String id) throws Exception;
}
