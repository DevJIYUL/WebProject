package com.home.service.apt;

import java.util.List;

import com.home.vo.apt.AptDto;
import com.home.vo.apt.DongDto;
import com.home.vo.apt.KeywordDto;
import com.home.vo.apt.SearchDto;

public interface AptService {
	List<DongDto> selectAllSido() throws Exception;

	List<DongDto> selectAllGugun(DongDto dongDto) throws Exception;

	List<DongDto> selectAllDong(DongDto dongDto) throws Exception;

	List<SearchDto> searchByDong(KeywordDto keywordDto) throws Exception;

	List<SearchDto> searchByAptName(String aptName)throws Exception;

	SearchDto selectOneByAptCode(KeywordDto keywordDto) throws Exception;

	AptDto selectLngLat(String aptCode)  throws Exception;;
}
