package com.home.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.home.service.quest.QuestService;
import com.home.vo.notice.FileInfoDto;
import com.home.vo.notice.Notice;
import com.home.vo.quest.Quest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
@CrossOrigin("*")
@RestController
@Api(value = "QuestController", description = "공지사항 컨트롤러")
public class QuestController {
	@Autowired
	private QuestService questService;
	
	@GetMapping("/api/quest")
	@ApiOperation(value = "questAll",notes = "QnA 전체 조회")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=204, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<List<Quest>> getQnaAll() throws Exception{
		List<Quest> list = questService.getQnaAll();
		if(list!=null) return new ResponseEntity<List<Quest>>(list, HttpStatus.OK);
		else return new ResponseEntity<List<Quest>>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/auth/quest/{id}")
	@ApiOperation(value = "questOne",notes = "QnA 상세 보기")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=204, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<Quest> QuestfindById(@PathVariable("id") String id) throws Exception{
		System.out.println(id);
		Quest quest = questService.QuestfindById(id);
		if(quest != null) {
			return new ResponseEntity<Quest>(quest, HttpStatus.OK);
		}
		else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@PostMapping("/auth/quest")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "quest",notes = "QnA 등록하기")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=204, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> registerQuest( @RequestBody Quest quest) throws Exception{
		System.out.println(quest);
		int x = questService.registerQuest(quest);
		System.out.println(x);
		if(x==1) return new ResponseEntity<String>("success",HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/auth/quest/{username}/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@ApiOperation(value = "quest",notes = "QnA 삭제하기")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=204, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> deleteQuest(@PathVariable("username") String username, @PathVariable("id") String id) throws Exception{
		int x = questService.deleteQuest(id);
		if(x==1) return new ResponseEntity<String>("success",HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@GetMapping("/api/quest/user/{username}")
	@ApiOperation(value = "quest",notes = "QnA 유저 검색하기")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=204, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<List<Quest>> serarchByUser(@PathVariable String username) throws Exception{
		List<Quest> quest = questService.searchByUser(username);
		System.out.println(username);
		if(quest != null)return new ResponseEntity<List<Quest>>(quest,HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@PutMapping("/auth/answer")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "quest",notes = "QnA 답장하기")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=204, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> answerQuest(@RequestBody Quest quest) throws Exception{
		int x = questService.answerQuest(quest);
		if(x==1) return new ResponseEntity<String>("success",HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/auth/quest")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "quest",notes = "QnA 수정하기")
	@ApiResponses({
		@ApiResponse(code=200, message ="success" ),
		@ApiResponse(code=204, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> modifyQuest(@RequestBody Quest quest) throws Exception{
		System.out.println(quest);
		int x = questService.modifyQuest(quest);
		System.out.println(x);
		if(x==1) return new ResponseEntity<String>("success",HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
