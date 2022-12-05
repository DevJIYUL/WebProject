package com.home.service.fav;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.mapper.FavoriteMapper;
import com.home.vo.user.FavoriteLoc;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService{
	@Autowired
	private final FavoriteMapper favMapper;

	@Override
	public FavoriteLoc checkFavorite(Map<String , String> map) throws Exception {
		return favMapper.checkFavorite(map);
	}

	@Override
	public List<FavoriteLoc> selectAllFav(String username) throws Exception {
		return favMapper.selectAllFav(username);
	}

	@Override
	public int addFavorite(FavoriteLoc favoriteLoc) throws Exception {
		return favMapper.addFavorite(favoriteLoc);
	}

	@Override
	public int deleteFavorite(FavoriteLoc favoriteLoc) throws Exception {
		return favMapper.deleteFavorite(favoriteLoc);
	}
	
	
}
