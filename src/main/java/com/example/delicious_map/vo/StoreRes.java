package com.example.delicious_map.vo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Store;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreRes {
	@JsonProperty("StoreFood_info")
	public Food foodentity;
	public String store_id;
	public String city;
	public String food;
	public Integer price;
	public Double point; 
	public String message; 
	public List<String> list; // api 4 and 5
	
	


	public StoreRes() {
	}

	public StoreRes(Food foodentity, String message) {
		this.foodentity = foodentity;
		this.message = message;
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

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public Food getFoodentity() {
		return foodentity;
	}

	public void setFoodentity(Food foodentity) {
		this.foodentity = foodentity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}


	


	

}
