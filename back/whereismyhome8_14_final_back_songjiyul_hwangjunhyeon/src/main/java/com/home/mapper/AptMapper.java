package com.home.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.home.vo.apt.AptDto;
import com.home.vo.apt.DongDto;
import com.home.vo.apt.KeywordDto;
import com.home.vo.apt.SearchDto;

@Mapper
public interface AptMapper {
	List<DongDto> selectAllSido() throws SQLException;

	List<DongDto> selectAllGugun(DongDto dongDto) throws SQLException;

	List<DongDto> selectAllDong(DongDto dongDto) throws SQLException;

	List<SearchDto> searchByDong(KeywordDto keywordDto) throws SQLException;

	List<SearchDto> searchByAptName(String aptName) throws SQLException;

	SearchDto selectOneByAptCode(KeywordDto keywordDto) throws SQLException;

	AptDto selectLngLat(String aptCode)  throws SQLException;
}