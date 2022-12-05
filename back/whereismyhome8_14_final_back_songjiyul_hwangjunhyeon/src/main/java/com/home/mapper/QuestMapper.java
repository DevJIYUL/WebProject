package com.home.mapper;

import java.sql.SQLException;  
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.vo.quest.Quest;


@Mapper
public interface QuestMapper {
	
	int registerQuest(Quest quest) throws SQLException;
	// ?
//	int editNotice(Notice notice) throws SQLException;
	
	List<Quest> getQnaAll() throws SQLException;
	
	Quest QuestfindById(String id) throws SQLException;

	int deleteQuest(String id);

	List<Quest> searchByUser(String username);

	int answerQuest(Quest quest);

	void answeredQuest(Quest quest);

	int modifyQuest(Quest quest);

	
//	int registerFile(Notice notice) throws SQLException;
	// ?
//	int getTotalNoticeCount(Map<String, Object> map) throws SQLException;

//	void updateHit(String id);
}
