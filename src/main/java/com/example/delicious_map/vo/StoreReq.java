package com.example.delicious_map.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreReq {
	@JsonProperty("store")
	public String store_id; //KEY ノㄓтStoreId and foodid 
	@JsonProperty("city")
	public String city;   //тStoreCity
	@JsonProperty("food")
	public String food; //тfood繺翴珇兜
	@JsonProperty("price")
	public int price; //тfood基
	@JsonProperty("foodpoint")
	public double foodpoint;//тfoodだ计  // api 5
	@JsonProperty("searchnumber")
	public int searchnumber;//砞﹚琩高戈掸计 // api 3
	@JsonProperty("searchpoint")
	public double searchpoint;//ノだ计тStore戈 // api 4 蛤 5
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

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public double getFoodpoint() {
		return foodpoint;
	}

	public void setFoodpoint(double foodpoint) {
		this.foodpoint = foodpoint;
	}

	public int getSearchnumber() {
		return searchnumber;
	}

	public void setSearchnumber(int searchnumber) {
		this.searchnumber = searchnumber;
	}

	public double getSearchpoint() {
		return searchpoint;
	}

	public void setSearchpoint(double searchpoint) {
		this.searchpoint = searchpoint;
	}

	
	

}
