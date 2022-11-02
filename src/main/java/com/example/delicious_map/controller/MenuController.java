package com.example.delicious_map.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.delicious_map.entity.MenuMenu;
import com.example.delicious_map.service.face.Menu_face;
import com.example.delicious_map.service.impl.Menu_impl;
import com.example.delicious_map.vo.MenuReq;
import com.example.delicious_map.vo.MenuRes;

@RestController
public class MenuController {
	@Autowired
	private Menu_face menu_face;

	// ==1.
	@PostMapping(value = "/api/menumenu1")
	public MenuRes addmenu(@RequestBody MenuReq req) {
		MenuRes res = new MenuRes();
		MenuMenu menu = menu_face.add(req.getMenuid(), req.getStoreid(), req.getName(), req.getPrice(),
				req.getEvaluation());
		if (menu == null) {
			res.setMenuid("���i�Ūŵ����̦h�����P");
			return res;
		}
		res.setMenuid(req.getMenuid());
		res.setStoreid(req.getStoreid());
		res.setName(req.getName());
		res.setPrice(req.getPrice());
		res.setEvaluation(req.getEvaluation());
		return res;

	}

	// ==2.
	@PostMapping(value = "/api/menumenu2")
	public MenuRes delete(@RequestBody MenuReq req) {
		MenuRes res = new MenuRes();
		MenuMenu menu = menu_face.delete(req.getMenuid());
		if (menu == null) {
			res.setMenuid("�Ū�");
			return res;
		}
		res.setMenuid("�w�R��" + req.getMenuid());
		return res;
	}

	// ==3.�d�ߵ���/����/���a
	@PostMapping(value = "/api/menumenu3")
	public MenuRes evaluation(@RequestBody MenuReq req) {
		MenuRes res = new MenuRes();
		List<MenuMenu> menulist = menu_face.getevaluation(req.getEvaluation(), req.getCity());
		res.setList(menulist);
		if(res.getList()==null||req.getEvaluation()>5) {
			res.setCity("�L�����a�έn�D�����L��");
			return res;
		}
		res.setCity("�Ӧ�"+req.getCity()+"�����a");
		return res;
	}
}
