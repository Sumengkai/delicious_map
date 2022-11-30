package com.example.delicious_map.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreReq {

	private String storeId; // KEY ノㄓтStoreId

	private String city; // тStoreCity

	private String foodName; // тfood繺翴珇兜

	private Integer price; // тfood基

	private double foodPoint; // тfoodだ计 // api 5

	private int limitNumber; // 砞﹚琩高戈掸计 // api 3

	private double searchPoint; // ノだ计тStore戈 // api 4 蛤 5

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

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public double getFoodPoint() {
		return foodPoint;
	}

	public void setFoodPoint(double foodPoint) {
		this.foodPoint = foodPoint;
	}

	public int getLimitNumber() {
		return limitNumber;
	}

	public void setLimitNumber(int limitNumber) {
		this.limitNumber = limitNumber;
	}

	public double getSearchPoint() {
		return searchPoint;
	}

	public void setSearchPoint(double searchPoint) {
		this.searchPoint = searchPoint;
	}

}
