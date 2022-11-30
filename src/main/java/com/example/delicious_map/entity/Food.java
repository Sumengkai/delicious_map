package com.example.delicious_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "food")
@IdClass(value = FoodId.class)
public class Food {
	@Id
	@Column(name = "store_id")
	private String storeId;

	@Id
	@Column(name = "food_id")
	private String foodId;

	@Column(name = "foodprice")
	private int foodPrice;

	@Column(name = "foodpoint")
	private double foodPoint;

	public Food() {
	}

	public Food(String storeId, String foodId, int foodPrice, double foodPoint) {
		this.storeId = storeId;
		this.foodId = foodId;
		this.foodPoint = foodPoint;
		this.foodPrice = foodPrice;
	}

	
	

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	public int getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(int foodPrice) {
		this.foodPrice = foodPrice;
	}

	public double getFoodPoint() {
		return foodPoint;
	}

	public void setFoodPoint(double foodPoint) {
		this.foodPoint = foodPoint;
	}

}
