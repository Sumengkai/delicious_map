package com.example.delicious_map.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.delicious_map.Constances.StoreFood_RtnCode;
import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.service.face.StoreFood;
import com.example.delicious_map.vo.StoreReq;
import com.example.delicious_map.vo.StoreRes;

@CrossOrigin
@RestController
public class StoreFoodController {
	@Autowired
	private StoreFood storeFood;
	// -------------------------
//	private StoreRes checkReq(StoreReq req) {
//		StoreRes res = new StoreRes();
//		if(req==null) {
//			res.setMessage("???", "???");
//			return res;
//		}
//		return null;
//		
//	}

	// -------------------------------------------------------------1.
	@PostMapping(value = "/api/addAndUpdateStore")
	public StoreRes addAndUpdateStore(@RequestBody StoreReq req) {
		return storeFood.addAndUpdateStore(req.getStoreId(), req.getCity());
	}

	// -------------------------------------------------------------2.
	@PostMapping(value = "/api/addAndUpdateFood")
	public StoreRes addAndUpdateFood(@RequestBody StoreReq req) {
		return storeFood.addAndUpdateFood(req.getStoreId(), req.getFoodName(), req.getPrice(), req.getFoodPoint());
	}

	// --------------------------------------------------------------3
	@PostMapping(value = "/api/searchByCityAndLimit")
	public StoreRes searchByCityAndLimit(@RequestBody StoreReq req) {
		StoreRes res = storeFood.searchByCityAndLimit(req.getCity(), req.getLimitNumber());
		return res;
	}

	// --------------------------------------------------------------4.(第四隻api的第二版本)
	@PostMapping(value = "/api/searchStorePoint")
	public StoreRes searchStorePoint(@RequestBody StoreReq req) {
		StoreRes res = storeFood.searchStorePoint(req.getSearchPoint());
		return res;
	}

	// --------------------------------------------------------------5.(第五隻api的第二版本)
	@PostMapping(value = "/api/searchStorePointAndFoodPoint")
	public StoreRes searchStorePointAndFoodPoint(@RequestBody StoreReq req) {
		StoreRes res = storeFood.searchStorePointAndFoodPoint(req.getSearchPoint(), req.getFoodPoint());
		return res;

	}
	// --------------------------------------------------------------4.
//	@PostMapping(value = "/api/searchStorePoint2")
//	public StoreRes searchStorePoint2(@RequestBody StoreReq req) {
//		StoreRes res = new StoreRes();
//		List<String> reslist = storeFood.searchStorePoint2(req.getSearchPoint());
//		if (reslist == null) {
//			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getCode(),
//					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
//			return res;
//		}
//		res.setList(reslist);
//		return res;
//	}
	// --------------------------------------------------------------5.
//	@PostMapping(value = "/api/searchStorePointAndFoodPoint")
//	public StoreRes searchStorePointAndFoodPoint(@RequestBody StoreReq req) {
//		StoreRes res = new StoreRes();
//		List<String> reslist = storeFood.searchStorePointAndFoodPoint(req.getSearchpoint(), req.getFoodPoint());
//		if (reslist == null) {
//			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage(),
//					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
//			return res;
//		}
//		res.setList(reslist);
//		return res;
//
//	}

}
