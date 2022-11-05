package com.example.delicious_map.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreReq {
	@JsonProperty("store")
	public String store_id; //�DKEY �Ψӧ�StoreId and foodid 
	@JsonProperty("city")
	public String city;   //��StoreCity
	@JsonProperty("food")
	public String food; //���food�\�I�~��
	@JsonProperty("price")
	public int price; //���food������
	@JsonProperty("foodpoint")
	public double foodpoint;//���food������  // api 5
	@JsonProperty("searchnumber")
	public int searchnumber;//�]�w�d�߸�ƪ����� // api 3
	@JsonProperty("searchpoint")
	public double searchpoint;//�Τ��Ƨ�Store����� // api 4 �� 5
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
