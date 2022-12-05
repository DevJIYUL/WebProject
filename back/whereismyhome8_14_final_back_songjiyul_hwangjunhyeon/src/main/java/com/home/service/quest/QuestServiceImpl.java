package com.home.service.quest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.home.mapper.QuestMapper;
import com.home.vo.quest.Quest;

@Service
public class QuestServiceImpl implements QuestService {
	@Autowired
	private QuestMapper questMapper;
	

	@Override
	public List<Quest> getQnaAll() throws Exception {
		// TODO Auto-generated method stub
		return questMapper.getQnaAll();
	}

	@Override
	public Quest QuestfindById(String id) throws Exception {
		// TODO Auto-generated method stub
		return questMapper.QuestfindById(id);
	}

	@Override
	public int registerQuest(Quest quest) throws Exception {
		// TODO Auto-generated method stub
		return questMapper.registerQuest(quest);
	}

	@Override
	public int deleteQuest(String id) {
		// TODO Auto-generated method stub
		return questMapper.deleteQuest(id);
	}

	@Override
	public List<Quest> searchByUser(String username) {
		// TODO Auto-generated method stub
		return questMapper.searchByUser(username);
	}

	@Override
	public int answerQuest(Quest quest) {
		return questMapper.answerQuest(quest);
	}

	@Override
	public int modifyQuest(Quest quest) {
		return questMapper.modifyQuest(quest);
	}
	
	
}
