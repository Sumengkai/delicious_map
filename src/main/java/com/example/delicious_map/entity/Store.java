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
	private String storeId;
	@Column(name = "city")
	private String city;
	@Column(name = "storepoint")
	private double point=0;

	// -----------------
	public Store() {
	}
	public Store(String storeId,String city,double point) {
		this.storeId=storeId;
		this.city=city;
		this.point=point;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

}
