package com.example.delicious_map.vo;

import java.util.List;

import com.example.delicious_map.entity.MenuMenu;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuRes {
	private String menuid;
	private String storeid;
	private String name;
	private String city;
	private Integer price;
	private Integer evaluation;
	private List<MenuMenu> list;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public List<MenuMenu> getList() {
		return list;
	}

	public void setList(List<MenuMenu> list) {
		this.list = list;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
