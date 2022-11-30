package com.example.delicious_map.entity;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class FoodId implements Serializable {
	// �@���ƦX�D�䪺���O�A������@Serializable����
	private String storeId;
	private String foodId;

	public FoodId() {
	}

	public FoodId(String storeId, String foodId) {
		this.foodId = foodId;
		this.storeId = storeId;
	}

	public String getStoreid() {
		return storeId;
	}

	public void setStoreid(String storeId) {
		this.storeId = storeId;
	}

	public String getFoodid() {
		return foodId;
	}

	public void setFoodid(String foodId) {
		this.foodId = foodId;
	}

}
