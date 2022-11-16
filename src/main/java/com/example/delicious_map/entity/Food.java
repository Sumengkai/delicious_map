package com.example.delicious_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
	private int foodprice;

	@Column(name = "foodpoint")
	private double foodpoint;

	public Food() {
	}

	public Food(String storeId, String foodId, int foodprice, double foodpoint) {
		this.storeId = storeId;
		this.foodId = foodId;
		this.foodpoint = foodpoint;
		this.foodprice = foodprice;
	}

	public String getStoreid() {
		return storeId;
	}

	public void setStoreid(String store_id) {
		this.storeId = store_id;
	}

	public String getFoodid() {
		return foodId;
	}

	public void setFoodid(String store) {
		this.foodId = store;
	}

	public int getFoodprice() {
		return foodprice;
	}

	public void setFoodprice(int foodprice) {
		this.foodprice = foodprice;
	}

	public double getFoodpoint() {
		return foodpoint;
	}

	public void setFoodpoint(double foodpoint) {
		this.foodpoint = foodpoint;
	}

}
