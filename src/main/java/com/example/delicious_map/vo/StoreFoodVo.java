package com.example.delicious_map.vo;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Store;
import com.fasterxml.jackson.annotation.JsonInclude;
@CrossOrigin
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreFoodVo {
	private Store store;
	private Food food;
	
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	


	

}
