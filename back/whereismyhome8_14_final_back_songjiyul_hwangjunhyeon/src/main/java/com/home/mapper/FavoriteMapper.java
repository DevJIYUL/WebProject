package com.home.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.home.vo.user.FavoriteLoc;

@Mapper
public interface FavoriteMapper {

	FavoriteLoc checkFavorite(Map<String , String> map) throws SQLException;

	List<FavoriteLoc> selectAllFav(String username) throws SQLException;

	int addFavorite(FavoriteLoc favoriteLoc) throws SQLException;

	int deleteFavorite(FavoriteLoc favoriteLoc) throws SQLException;
	
}
