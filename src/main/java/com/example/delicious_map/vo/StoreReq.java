package com.example.delicious_map.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreReq {
	@JsonProperty("store")
	private String store_id;
	@JsonProperty("city")
	private String city;
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
	
}
