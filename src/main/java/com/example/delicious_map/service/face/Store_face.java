package com.example.delicious_map.service.face;

import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.Store;

public interface Store_face {
	public Store addandupdatestore(String store, String city); //�W�[�M�ק�
	public Store delete(String store);//�R��
	public List<Store> find(String city);//�z�L�����䩱�a
	public Store findstore(String store);//�䩱�a
	
		
}
