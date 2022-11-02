package com.example.delicious_map.service.face;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.MenuMenu;
import com.example.delicious_map.entity.Store;

public interface Menu_face {
	public MenuMenu add(String name1, String name2, String name3, int name4, int name5);//增加和修改
	public MenuMenu delete(String id);//刪除
	public List<MenuMenu> getevaluation(int evaluation, String city);//透過城市和餐點評價搜尋要的東西
	
}
