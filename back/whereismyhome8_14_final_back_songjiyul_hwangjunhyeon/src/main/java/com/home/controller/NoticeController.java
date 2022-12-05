package com.home.controller;

import java.io.File; 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.home.service.notice.NoticeService;
import com.home.vo.notice.FileInfoDto;
import com.home.vo.notice.Notice;
import com.home.vo.notice.NoticeModify;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
@CrossOrigin("*")
@RestController
@Api(value = "NoticeController", description = "공지사항 컨트롤러")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	
	@Value("${file.path.upload-files}")
	String filePath;
	
	
	@GetMapping("/api/notice")
	@ApiOperation(value = "noticeAll",notes = "공지사항 전체 조회")
	@ApiResponses({
		@ApiResponse(code=200, message ="succes" ),
		@ApiResponse(code=202, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<List<Notice>> noticeall() throws Exception{
		List<Notice> list = noticeService.listNotice();
		if(list!=null) return new ResponseEntity<List<Notice>>(list, HttpStatus.OK);
		else return new ResponseEntity<List<Notice>>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/api/notice/{id}")
	@ApiOperation(value = "noticeOne",notes = "공지사항 상세 보기")
	@ApiResponses({
		@ApiResponse(code=200, message ="succes" ),
		@ApiResponse(code=202, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<Notice> detailNotice(@PathVariable("id") String id) throws Exception{
		Notice notice = noticeService.detailNotice(id);
		if(notice != null) {
			noticeService.updateHit(id);
			return new ResponseEntity<Notice>(notice, HttpStatus.OK);
		}
		else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/auth/notice")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "notice",notes = "공지사항 등록하기",consumes = "MediaType.MULTIPART_FORM_DATA_VALUE")
	@ApiResponses({
		@ApiResponse(code=200, message ="succes" ),
		@ApiResponse(code=202, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> registerNotice(
			 Notice notice,
			 @RequestParam(value = "addInfos",required = false) MultipartFile[] files
			) throws Exception{
		System.out.println(notice.toString());
		System.out.println(filePath);
		System.out.println(files);
		// 첨부 자료 있는 지 확인
		if(files != null && !files[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = filePath + File.separator + today;
			File folder = new File(saveFolder);
			if(!folder.exists())folder.mkdirs();
			List<FileInfoDto> fileInfos = new ArrayList<>();
			for (MultipartFile mfile : files) {
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfoDto);
			}
			notice.setFileInfos(fileInfos);
		}
		// 공지사항 쓰기
		int x = noticeService.registerNotice(notice);
		if(x==1) return new ResponseEntity<String>("sucess",HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@PostMapping("/auth/modify")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "notice",notes = "공지사항 수정하기",consumes = "MediaType.MULTIPART_FORM_DATA_VALUE")
	@ApiResponses({
		@ApiResponse(code=200, message ="succes" ),
		@ApiResponse(code=202, message ="no_content" ),
		@ApiResponse(code=404, message ="페이지 연결 안됨" ),
		@ApiResponse(code=500, message ="서버 에러" ),
	})
	public ResponseEntity<?> editNotice(
			NoticeModify noticeModify,
//			@RequestParam(value = "deleteInfos")String[] deleteinfos,
			@RequestParam(value = "addInfos",required = false) MultipartFile[] files) throws Exception{
//		System.out.println(deleteinfos.toString());
		System.out.println("noticeModify");
		System.out.println(noticeModify);
		System.out.println(files);
		if(files != null && !files[0].isEmpty()) {
			for (MultipartFile multipartFile : files) {
				System.out.println(multipartFile.getOriginalFilename());
			}
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = filePath + File.separator + today;
			File folder = new File(saveFolder);
			if(!folder.exists())folder.mkdirs();
			List<FileInfoDto> fileInfos = new ArrayList<>();
			for (MultipartFile mfile : files) {
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfoDto);
			}
			noticeModify.setAddinfo(fileInfos);
		}
		System.out.println("delete 할 파일 이름 : "+noticeModify.getDeleteInfos());
		System.out.println("add 할 파일 이름 : "+noticeModify.getAddinfo());
		int x = noticeService.editNotice(noticeModify);
		System.out.println("this is return => "+x);
		if(x == 1) return new ResponseEntity<String>("success", HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	@PostMapping(value = "/api/download")
	public ResponseEntity<Object> downloadFile(@Value("${file.path.upload-files}") String filepath
			,@RequestBody FileInfoDto down
//			,@RequestParam(value = "sfolder",required = false) String sfolder
//			, @RequestParam(value = "ofile",required = false) String ofile
//			,@RequestParam("sfile") String sfile
			, HttpSession session) {
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if (memberDto != null) {
//			Map<String, Object> fileInfo = new HashMap<String, Object>();
//			fileInfo.put("sfolder", sfolder);
//			fileInfo.put("ofile", ofile);
//			fileInfo.put("sfile", sfile);
		System.out.println(down);
		String sfolder = down.getSaveFolder();
		String sfile = down.getSaveFile();
		String path = filepath+"/"+sfolder+"/"+sfile;
		System.out.println(filepath);
		System.out.println(sfolder);
		System.out.println(sfile);
		System.out.println(path);
		try {
			
			Path filePath = Paths.get(path);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath));
			File file = new File(path);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
//		}
	}
}
