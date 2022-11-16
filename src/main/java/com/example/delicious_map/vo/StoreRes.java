package com.example.delicious_map.vo;

import java.util.List;


import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Store;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreRes {
	@JsonProperty("StoreFood_Info") //顯示給人看ㄉ
	private Food foodentity;
	@JsonProperty("Store_Info")
	private Store store;
	private String error; //錯誤code
	private String message; //message
	private List<String> list; // api 4 and 5
	



	public StoreRes() {
	}
	public StoreRes(Store store,String message) {
		this.store = store;
		this.message = message;
	}

	public StoreRes(Food foodentity, String message) {
		this.foodentity = foodentity;
		this.message = message;
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

	public void setMessage(String code,String message) {
		this.error=code;
		this.message = message;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

	


//	public String getStore_id() {
//		return store_id;
//	}
//
//	public void setStore_id(String store_id) {
//		this.store_id = store_id;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getFood() {
//		return food;
//	}
//
//	public void setFood(String food) {
//		this.food = food;
//	}
//
//	public Integer getPrice() {
//		return price;
//	}
//
//	public void setPrice(Integer price) {
//		this.price = price;
//	}
//
//	public Double getPoint() {
//		return point;
//	}
//
//	public void setPoint(Double point) {
//		this.point = point;
//	}



	

}
