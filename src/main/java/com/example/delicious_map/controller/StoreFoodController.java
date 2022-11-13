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
	private StoreFood storeFood_face;

	// --------1.
	@PostMapping(value = "/api/StoreFood1")
	public StoreRes AddAndUpdateStore(@RequestBody StoreReq req) {
		StoreRes res =storeFood_face.AddAndUpdateStore(req.getStore_id(), req.getCity());
		return res;
	}

	// --------2.
	@PostMapping(value = "/api/StoreFood2")
	public StoreRes AddAndUpdateFood(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		Food food = storeFood_face.AddAndUpdateFood(req.getStore_id(), req.getFood(), req.getPrice(), req.getFoodpoint());
		if (food == null) {
			if (!StringUtils.hasText(req.getStore_id()) || !StringUtils.hasText(req.getFood())) {
				return new StoreRes(null, StoreFood_RtnCode.CANT_FIND_Key.getMessage());
			} else if (req.getPrice() <= 0) {
				return new StoreRes(null, StoreFood_RtnCode.CANT_FIND_price.getMessage());
			} else if (req.getFoodpoint() < 1 || req.getFoodpoint() > 5) {
				return new StoreRes(null, StoreFood_RtnCode.CANT_FIND_point.getMessage());
			} else {
				return new StoreRes(null, StoreFood_RtnCode.CANT_FIND_Regulation.getMessage());
			}
		}
		return new StoreRes(food, StoreFood_RtnCode.SUCCESSFUL.getMessage());

	}

	// --------3.
	@PostMapping(value = "/api/StoreFood3")
	public StoreRes SearchByCityList(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		List<String> list = storeFood_face.SearchByCity(req.getCity(), req.getSearchnumber());
		if (list == null) {
			return new StoreRes(null, StoreFood_RtnCode.CANT_FIND_LIST.getMessage());
		}
		res.setList(list);
		return res;
	}

	// --------4.
	@PostMapping(value = "/api/StoreFood4")
	public StoreRes SearchByStorePoint(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		List<String> reslist = storeFood_face.SearchStorePoint(req.getSearchpoint());
		if (req.getSearchpoint()<1||req.getSearchpoint()>=6) {
			return new StoreRes(null, StoreFood_RtnCode.CANT_FIND_POINTLIST.getMessage());
		}
		res.setList(reslist);
		return res;
	}

	// --------5.
	@PostMapping(value = "/api/StoreFood5")
	public StoreRes SearchByStorepointAndFoodpoint(@RequestBody StoreReq req) {
		StoreRes res = new StoreRes();
		List<String> reslist = storeFood_face.SearchStorePointAndFoodPoint(req.getSearchpoint(), req.getFoodpoint());
		if (req.getSearchpoint()>=6||req.getSearchpoint()<1|| req.getFoodpoint()<1||req.getFoodpoint()>=6) {
			return new StoreRes(null, StoreFood_RtnCode.CANT_FIND_POINTLIST.getMessage());
		}
		res.setList(reslist);
		return res;

	}

}
