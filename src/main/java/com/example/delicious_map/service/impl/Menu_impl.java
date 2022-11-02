package com.example.delicious_map.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.MenuMenu;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.repository.Menudao;
import com.example.delicious_map.repository.StoreDao;
import com.example.delicious_map.service.face.Menu_face;
import com.example.delicious_map.vo.StoreMenu_Res;

@Service
public class Menu_impl implements Menu_face {
	@Autowired
	private Menudao menudao;
	@Autowired
	private StoreDao storeDao;
	

	// ==增加.修改
	public MenuMenu add(String name1, String name2, String name3, int name4, int name5) {
		MenuMenu x = new MenuMenu();
		menudao.findById(name1);
		if (!StringUtils.hasText(name1) || name5 > 5) {
			return null;
		}

		x.setMenuid(name1);
		x.setStoreid(name2);
		x.setName(name3);
		x.setPrice(name4);
		x.setEvaluation(name5);
		menudao.save(x);

		return x;
	}

	// ==刪除
	public MenuMenu delete(String id) {
		MenuMenu delete = new MenuMenu();
		if (!StringUtils.hasText(id)) {
			return null;
		}
		Optional<MenuMenu> menuop = menudao.findById(id);
		if (!menuop.isPresent()) {
			return null;
		} else {
			menudao.deleteById(id);
		}
		return delete;

	}

	// ==評價搜尋
	public List<MenuMenu> getevaluation(int evaluation, String city) {
		List<MenuMenu> xxx = new ArrayList<>();
		List<MenuMenu> menuList = menudao.findByEvaluationGreaterThanEqual(evaluation); // 找到大於請求的評價
		List<Store> cityStore = storeDao.findByCity(city);
		if (cityStore == null || cityStore.isEmpty()) {
			return null;
		}
		for (var x : menuList) { // 掃menu的
			for (var xx : cityStore) {// 掃Store的
				if (x.getStoreid().equals(xx.getStore_id())) {
					xxx.add(x);

				}
			}
		}
		return xxx;
	}

}
