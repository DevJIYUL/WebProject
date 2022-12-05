package com.home.mapper;

import java.sql.SQLException; 
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.home.vo.notice.FileInfoDto;
import com.home.vo.notice.Notice;
import com.home.vo.notice.NoticeModify;

@Mapper
public interface NoticeMapper {
	
	int registerNotice(Notice notice) throws SQLException;
	
	List<Notice> listNotice() throws SQLException;
	
	Notice detailNotice(String id) throws SQLException;
	
	int registerFile(Notice notice) throws SQLException;
	// ?
	int getTotalNoticeCount(Map<String, Object> map) throws SQLException;

	void updateHit(String id);
	
	int editNotice(Notice notice) throws SQLException;
	void editFile(Notice notice);
	void deleteFile(NoticeModify notocemodi);
}
