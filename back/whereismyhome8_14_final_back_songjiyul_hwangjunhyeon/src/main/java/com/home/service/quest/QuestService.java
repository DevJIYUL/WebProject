package com.home.service.quest;

import java.util.List; 

import com.home.vo.quest.Quest;

public interface QuestService {
//	int registerNotice(Notice notice) throws Exception;
	// ?
//	int editNotice(Notice notice) throws Exception;
	
	List<Quest> getQnaAll() throws Exception;
	
	Quest QuestfindById(String id) throws Exception;

	int registerQuest(Quest quest) throws Exception;

	int deleteQuest(String id);

	List<Quest> searchByUser(String username);

	int answerQuest(Quest quest);

	int modifyQuest(Quest quest);
	
//	int registerFile(Notice notice) throws Exception;
	// ?
//	int getTotalNoticeCount(Map<String, Object> map) throws Exception;

//	void updateHit(String id);
}
