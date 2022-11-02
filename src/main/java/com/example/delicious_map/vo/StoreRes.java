package com.example.delicious_map.vo;

import java.util.List;

import com.example.delicious_map.entity.Store;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreRes {
	private String store_id;
	private String city;
	private List<Store>list;
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<Store> getList() {
		return list;
	}
	public void setList(List<Store> list) {
		this.list = list;
	}
	
}
