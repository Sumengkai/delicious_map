package com.example.delicious_map.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.delicious_map.entity.Store;
import com.example.delicious_map.service.impl.Store_impl;
import com.example.delicious_map.vo.StoreReq;
import com.example.delicious_map.vo.StoreRes;

@RestController
public class CityStoreController {
	@Autowired
	private Store_impl store_face;

	// ==1.
	@PostMapping(value = "/api/store1")
	public StoreRes addstore(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		Store store = store_face.addandupdatestore(req.getStore_id(), req.getCity());
		if (store == null) {
			res.setStore_id("不可空空");
			return res;
		}
		res.setStore_id(req.getStore_id());
		res.setCity(req.getCity());
		return res;
	}

	// ==2.
	@PostMapping(value = "/api/store2")
	public StoreRes deletestore(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		Store store = store_face.delete(req.getStore_id());
		if (store == null) {
			res.setStore_id("不可空空");
			return res;
		}
		res.setStore_id("已刪除" + req.getStore_id());
		return res;
	}

	// ==3.
	@PostMapping(value = "/api/store3")
	public StoreRes findbycity(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		res.setList(store_face.find(req.getCity()));
		return res;

	}
	@PostMapping(value = "/api/store4")
	public StoreRes findbyid(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		Store store = store_face.findstore(req.getStore_id());
		if(store==null) {
			res.setStore_id("無");
			return res;
		}
		res.setStore_id(store.getStore_id());
		res.setCity(store.getCity());
		return res;
	}

}
