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
	@JsonProperty("StoreFood_Info") // 顯示給人看的
	private Food food;
	@JsonProperty("Store_Info")
	private Store store;
	private String errorCode; // 錯誤code
	private String message; // message
	private List<String> list; // api 4 and 5(沒用ㄌ)
	private List<StoreFoodVo> voList; // api 3 4 5
	private Set<StoreFoodVo> treeSet;

	public StoreRes() {
	}

	public StoreRes(Store store, String message) {
		this.store = store;
		this.message = message;
	}

	public StoreRes(Food food, String message) {
		this.food = food;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String code, String message) {
		this.errorCode = code;
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
	

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String error) {
		this.errorCode = error;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<StoreFoodVo> getVoList() {
		return voList;
	}

	public void setVoList(List<StoreFoodVo> voList) {
		this.voList = voList;
	}
	//-

	public Set<StoreFoodVo> getTreeSet() {
		return treeSet;
	}

	public void setTreeSet(Set<StoreFoodVo> treeSet) {
		this.treeSet = treeSet;
	}
	

}
