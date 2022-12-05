package com.home.service.notice;

import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.home.mapper.NoticeMapper;
import com.home.vo.notice.FileInfoDto;
import com.home.vo.notice.Notice;
import com.home.vo.notice.NoticeModify;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeMapper noticeMapper;
	@Override
	@Transactional
	public int registerNotice(Notice notice) throws Exception {
		// TODO Auto-generated method stub
		int result = noticeMapper.registerNotice(notice);
		List<FileInfoDto> fileInfos = notice.getFileInfos();
		int file = 0;
		System.out.println(fileInfos);
		if(fileInfos != null && !fileInfos.isEmpty()) {
			file = noticeMapper.registerFile(notice);
		}
		return result ;
	}
	@Override
	public int editNotice(NoticeModify noticemodify) throws Exception {
		// TODO Auto-generated method stub
		// 삭제할 파일
		System.out.println("삭제할 데이터"+noticemodify.getDeleteInfos());
		List<String> deletefile = noticemodify.getDeleteInfos();
		
		// 파일 추가할것은 따로 나눈다.
		System.out.println("추가할 데이터"+noticemodify.getAddinfo());
		List<FileInfoDto>fileInfos = noticemodify.getAddinfo();
		// 수정할 파일을 notice객체에 담아사보낸다.
		Notice noticemodi = new Notice();
		noticemodi.setNoticeNo(noticemodify.getNoticeNo());
		noticemodi.setTitle(noticemodify.getTitle());
		noticemodi.setContent(noticemodify.getContent());
		noticemodi.setFileInfos(noticemodify.getAddinfo());
		// noticemodi는 먼저 변경 해준다
		int strmodi = noticeMapper.editNotice(noticemodi);
		System.out.println(strmodi);
		// 추가할 파일이 있으면 파일 추가
		if(fileInfos != null && !fileInfos.isEmpty()) {
			System.out.println("이곳은 파일을 추가합니다");
			noticeMapper.editFile(noticemodi);
		}
		// 삭제할 파일이 있으면삭제한다.
		if(deletefile != null && !deletefile.isEmpty()) {
			System.out.println("이곳은 파일을 삭제합니다");
			noticeMapper.deleteFile(noticemodify);
		}
		return strmodi;
	}
	@Override
	public List<Notice> listNotice() throws Exception {
		return noticeMapper.listNotice();
	}

	@Override
	public Notice detailNotice(String id) throws Exception {
		// TODO Auto-generated method stub
		Notice temp =noticeMapper.detailNotice(id);
		System.out.println(temp.toString());
		return temp;
	}

	@Override
	public void updateHit(String id) throws Exception {
		noticeMapper.updateHit(id);
	}

}
