package com.example.delicious_map.service.face;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.MenuMenu;
import com.example.delicious_map.entity.Store;

public interface Menu_face {
	public MenuMenu add(String name1, String name2, String name3, int name4, int name5);//�W�[�M�ק�
	public MenuMenu delete(String id);//�R��
	public List<MenuMenu> getevaluation(int evaluation, String city);//�z�L�����M�\�I�����j�M�n���F��
	
}
