package com.example.delicious_map.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.delicious_map.Constances.StoreFood_RtnCode;
import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.service.face.StoreFood;
import com.example.delicious_map.vo.StoreReq;
import com.example.delicious_map.vo.StoreRes;

@RestController
public class StoreFoodController {
	@Autowired
	private StoreFood storeFood;

	// -------------------------------------------------------------1.
	@PostMapping(value = "/api/addAndUpdateStore")
	public StoreRes addAndUpdateStore(@RequestBody StoreReq req) {
		return storeFood.addAndUpdateStore(req.getStore_id(), req.getCity());
	}

	// -------------------------------------------------------------2.
	@PostMapping(value = "/api/addAndUpdateFood")
	public StoreRes addAndUpdateFood(@RequestBody StoreReq req) {
		return storeFood.addAndUpdateFood(req.getStore_id(), req.getFood(), req.getPrice(), req.getFoodpoint());
	}

	//--------------------------------------------------------------3.
	@PostMapping(value = "/api/searchByCityAndLimit")
	public StoreRes searchByCityAndLimit(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		List<String> list = storeFood.searchByCityAndLimit(req.getCity(), req.getSearchnumber());
		if (list == null) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_CITYLIST.getCode(),StoreFood_RtnCode.CANT_FIND_CITYLIST.getMessage());
			return res;
		}
		res.setList(list);
		return res;
	}

	//--------------------------------------------------------------4.
	@PostMapping(value = "/api/searchStorePoint")
	public StoreRes searchStorePoint(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		List<String> reslist = storeFood.searchStorePoint(req.getSearchpoint());
		if (reslist==null) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_POINTLIST.getCode(),StoreFood_RtnCode.CANT_FIND_POINTLIST.getMessage());
			return res;
		}
		res.setList(reslist);
		return res;
	}

	//--------------------------------------------------------------5.
	@PostMapping(value = "/api/searchStorePointAndFoodPoint")
	public StoreRes searchStorePointAndFoodPoint(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		List<String> reslist = storeFood.searchStorePointAndFoodPoint(req.getSearchpoint(), req.getFoodpoint());
		if (reslist==null) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_POINTLIST.getMessage(),StoreFood_RtnCode.CANT_FIND_POINTLIST.getMessage());
			return res;
		}
		res.setList(reslist);
		return res;

	}

}
