package com.home.service.apt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.mapper.AptMapper;
import com.home.vo.apt.AptDto;
import com.home.vo.apt.DongDto;
import com.home.vo.apt.KeywordDto;
import com.home.vo.apt.SearchDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService{
	@Autowired
	private final AptMapper aptMapper;

	@Override
	public List<DongDto> selectAllSido() throws Exception {
		return aptMapper.selectAllSido();
	}

	@Override
	public List<DongDto> selectAllGugun(DongDto dongDto) throws Exception {
		return aptMapper.selectAllGugun(dongDto);
	}

	@Override
	public List<DongDto> selectAllDong(DongDto dongDto) throws Exception {
		return aptMapper.selectAllDong(dongDto);
	}

	@Override
	public List<SearchDto> searchByDong(KeywordDto keywordDto) throws Exception {
		return aptMapper.searchByDong(keywordDto);
	}

	@Override
	public List<SearchDto> searchByAptName(String aptName) throws Exception {
		return aptMapper.searchByAptName(aptName);
	}

	@Override
	public SearchDto selectOneByAptCode(KeywordDto keywordDto) throws Exception {
		return aptMapper.selectOneByAptCode(keywordDto);
	}

	@Override
	public AptDto selectLngLat(String aptCode) throws Exception {
		return aptMapper.selectLngLat(aptCode);
	}
	
	
	
}
