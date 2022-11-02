package com.example.delicious_map.service.face;

import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.Store;

public interface Store_face {
	public Store addandupdatestore(String store, String city); //增加和修改
	public Store delete(String store);//刪除
	public List<Store> find(String city);//透過城市找店家
	public Store findstore(String store);//找店家
	
		
}
