package com.example.delicious_map.entity;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class FoodId implements Serializable {
	// 作為複合主鍵的類別，必須實作Serializable介面
	private String storeId;
	private String foodId;

	public FoodId() {
	}

	public FoodId(String storeid, String foodid) {
		this.foodId = foodid;
		this.storeId = storeid;
	}

	public String getStoreid() {
		return storeId;
	}

	public void setStoreid(String storeid) {
		this.storeId = storeid;
	}

	public String getFoodid() {
		return foodId;
	}

	public void setFoodid(String foodid) {
		this.foodId = foodid;
	}

}
