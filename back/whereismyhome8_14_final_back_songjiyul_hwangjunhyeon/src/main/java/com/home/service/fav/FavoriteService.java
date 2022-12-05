package com.home.service.fav;

import java.util.List;
import java.util.Map;

import com.home.vo.user.FavoriteLoc;

public interface FavoriteService {

	FavoriteLoc checkFavorite(Map<String , String> map) throws Exception;

	List<FavoriteLoc> selectAllFav(String username) throws Exception;

	int addFavorite(FavoriteLoc favoriteLoc) throws Exception;

	int deleteFavorite(FavoriteLoc favoriteLoc) throws Exception;

}
