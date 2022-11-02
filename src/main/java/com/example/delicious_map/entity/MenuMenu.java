package com.example.delicious_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menumenu")
public class MenuMenu {
	@Id
	@Column(name = "menuid")
	private String menuid;
	@Column(name = "storeid")
	private String storeid;
	@Column(name = "name")
	private String name;
	@Column(name = "price")
	private int price;
	@Column(name = "evaluation")
	private int evaluation;
	//==
	public MenuMenu() {}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
		
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}

}
