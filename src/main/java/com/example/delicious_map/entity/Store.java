package com.example.delicious_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "store")
public class Store {
	@Id
	@Column(name = "store_id")
	private String store_id;
	@Column(name = "city")
	private String city;
	//==
	public Store() {}
	public Store(String store,String city) {
		this.city=city;
		this.store_id=store;
	}
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
