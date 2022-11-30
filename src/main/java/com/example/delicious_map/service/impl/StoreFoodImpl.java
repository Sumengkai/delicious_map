package com.example.delicious_map.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.delicious_map.Constances.StoreFood_RtnCode;
import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.FoodId;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.repository.FoodDao;
import com.example.delicious_map.repository.StoreDao;
import com.example.delicious_map.service.face.StoreFood;
import com.example.delicious_map.vo.StoreFoodVo;
import com.example.delicious_map.vo.StoreRes;

@Service
public class StoreFoodImpl implements StoreFood {
//----------------------------	
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodDao foodDao;

// ---------------------------
	private StoreRes checkInfo(String store, String food, Integer price, double point) {
		StoreRes res = new StoreRes();
		if (!StringUtils.hasText(store) || !StringUtils.hasText(food)) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STOREORFOOD.getCode(),
					StoreFood_RtnCode.CANT_FIND_STOREORFOOD.getMessage());
			return res;
		}

		if (price == null || price < 0) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_PRICE.getCode(), StoreFood_RtnCode.CANT_FIND_PRICE.getMessage());
			return res;
		}

		if (point > 5 || point < 1) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_POINT.getCode(), StoreFood_RtnCode.CANT_FIND_POINT.getMessage());
			return res;
		}
		return null;
	}

	// =========1.新增和修改Store - API 1
	@Override
	public StoreRes addAndUpdateStore(String store, String city) {
		Store storeInfo = new Store();
		StoreRes res = new StoreRes();
		if (!StringUtils.hasText(store) || !StringUtils.hasText(city)) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORE.getCode(), StoreFood_RtnCode.CANT_FIND_STORE.getMessage()); // 防空空
			return res;
		}
		Optional<Store> storeOp = storeDao.findById(store);

		// 掉進去代表要新增,因為找不到主key
		if (!storeOp.isPresent()) {
			storeInfo.setStoreId(store);
			storeInfo.setCity(city);
			storeDao.save(storeInfo);
			return new StoreRes(storeInfo, StoreFood_RtnCode.ADD_SUCCESSFUL.getMessage());
		}

		// 上面沒return掉代表就是要修改
		storeInfo = storeOp.get();
		storeInfo.setCity(city);
		storeDao.save(storeInfo);
		return new StoreRes(storeInfo, StoreFood_RtnCode.UPDATE_SUCCESSFUL.getMessage());
	}

	// =========2.新增和修改Food並且存到store的評價 - API 2
	@Override
	public StoreRes addAndUpdateFood(String store, String food, Integer price, double point) {
		Food foodInfo = new Food();
		// -----防呆內容
		StoreRes res = checkInfo(store, food, price, point);
		if (res != null) {
			return res;
		}

		// 用於確認新增或修改的布林值,回傳不同的訊息給用戶
		boolean addOrUpdate = false;

		FoodId foodId = new FoodId(store, food); // 雙主KEY

		// 確認有沒有撞雙主KEY 有的話代表是修改
		Optional<Food> foodOp = foodDao.findById(foodId);

		// 不能新增不在店家內的餐點要撈的資料
		Optional<Store> storeOp = storeDao.findById(store);

		if (foodOp.isPresent()) { // 修改的判斷式,掉進去代表要修改餐點資訊
			foodInfo = foodOp.get();
			foodInfo.setFoodPrice(price);
			foodInfo.setFoodPoint(Math.floor(point * 10.0) / 10.0); // 無條件捨去至小數點後 1 位
			foodDao.save(foodInfo);
		} else if (storeOp.isPresent()) { // 掉進去代表要新增餐點,"且"不能新增不在店家內的餐點
			foodInfo.setFoodId(food);
			foodInfo.setStoreId(store);
			foodInfo.setFoodPrice(price);
			foodInfo.setFoodPoint(Math.floor(point * 10.0) / 10.0);
			foodDao.save(foodInfo);
			addOrUpdate = true; // "新增"的布林
		} else {
			res = new StoreRes();
			res.setMessage(StoreFood_RtnCode.CANT_FIND_REGULATION_STORE.getCode(),
					StoreFood_RtnCode.CANT_FIND_REGULATION_STORE.getMessage()); // 不能新增不在店家內的餐點的訊息
			return res;
		}

		// ------------------------------------------------藉由餐點評價分數算出平均存到店家欄位

		// 存進店家分數欄位的容器
		double someFoodPoint = 0;

		Store storeInfo = storeOp.get();

		// 撈出所有相對店名的資料
		List<Food> storeList = foodDao.findByStoreId(store);

		// 先全部把評分加起來
		for (var foodfor : storeList) {
			someFoodPoint += foodfor.getFoodPoint();
		}
		// 同家店有多少餐點就有幾個size
		someFoodPoint = someFoodPoint / storeList.size();

		// 無條件捨去至小數點後 1 位
		storeInfo.setPoint(Math.floor(someFoodPoint * 10.0) / 10.0);
		storeDao.save(storeInfo);

		// 根據修改或新增給出不同訊息
		if (addOrUpdate) {
			return new StoreRes(foodInfo, StoreFood_RtnCode.ADD_SUCCESSFUL.getMessage()); // 新增訊息
		}
		return new StoreRes(foodInfo, StoreFood_RtnCode.UPDATE_SUCCESSFUL.getMessage()); // 修改訊息

	}

	// =========3.藉由城市搜尋到店家等資訊,並且限制筆數 - API 3
	@Override
	public StoreRes searchByCityAndLimit(String city, int limitNumber) {
		StoreRes res = new StoreRes();
		if (!StringUtils.hasText(city)) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_PARAMETER_CITY.getCode(),
					StoreFood_RtnCode.CANT_FIND_PARAMETER_CITY.getMessage()); // 防空空
			return res;
		}
		List<StoreFoodVo> voList = new ArrayList<>();

		List<Store> storeInfoList = storeDao.findByCity(city);
		if (storeInfoList.isEmpty()) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_CITYLIST.getCode(),
					StoreFood_RtnCode.CANT_FIND_CITYLIST.getMessage());
			return res;
		}

		List<String> storeIdList = new ArrayList<>();
		for (var storeIdItem : storeInfoList) {
			storeIdList.add(storeIdItem.getStoreId());
		}
		List<Food> foodInfoList = foodDao.findByStoreIdIn(storeIdList);
		// 需先跑food外層的迴圈,因為我需要將食物的資訊以及店家的資訊存在同一個list與api4/5不同,因為最後需要切割list
		for (var foodItem : foodInfoList) {
			for (var storeItem : storeInfoList) {
				if (foodItem.getStoreId().equals(storeItem.getStoreId())) {
					StoreFoodVo storeAndFoodInfo = new StoreFoodVo();
					storeAndFoodInfo.setStore(storeItem);
					storeAndFoodInfo.setFood(foodItem);
					voList.add(storeAndFoodInfo);
				}
			}
		}
		
		// 當limitNumber小於零或是空時 亦或是 limitNumber大於我的list , 回傳整個list
		if (limitNumber <= 0 || limitNumber > voList.size()) {
			res.setVoList(voList);
			return res;
		}
		// 上面沒return掉代表需要切list
		res.setVoList(voList.subList(0, limitNumber));
		return res;
	}

	// =========4.藉由評價搜尋到店家等資訊,並且排序 - API 4(第二版本)
	@Override
	public StoreRes searchStorePoint(double point) {
		StoreRes res = new StoreRes();
		if (point < 1 || point > 5) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getCode(),
					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
			return res;
		}
		List<StoreFoodVo> voList = new ArrayList<>();
		List<String> storeIdList = new ArrayList<>();
		List<Store> storeInfoList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point);
		if (storeInfoList.isEmpty()) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getCode(),
					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
			return res;
		}
		for (var storeItem : storeInfoList) {
			storeIdList.add(storeItem.getStoreId());
		}
		List<Food> foodInfoList = foodDao.findByStoreIdIn(storeIdList);
		for (var storeItem : storeInfoList) {
			StoreFoodVo storeAndFoodInfo = new StoreFoodVo();
			storeAndFoodInfo.setStore(storeItem);
			voList.add(storeAndFoodInfo);
			for (var foodItem : foodInfoList) {
				if (storeItem.getStoreId().equals(foodItem.getStoreId())) {
					storeAndFoodInfo = new StoreFoodVo();
					storeAndFoodInfo.setFood(foodItem);
					voList.add(storeAndFoodInfo);
				}
			}
		}
		res.setVoList(voList);
		return res;
	}

	// =========5.藉由店家評價找出店家and藉由菜單評價找出菜單 - API 5(第二版本)
	@Override
	public StoreRes searchStorePointAndFoodPoint(double storepoint, double foodpoint) {
		StoreRes res = new StoreRes();
		if (storepoint < 1 || storepoint > 5 || foodpoint < 1 || foodpoint > 5) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage(),
					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
			return res;
		}
		List<StoreFoodVo> voList = new ArrayList<>();

		List<Store> storeInfoList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storepoint);
		if (storeInfoList.isEmpty()) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage(),
					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
			return res;
		}
		List<String> storeIdList = new ArrayList<>();
		for (var storeItem : storeInfoList) {
			storeIdList.add(storeItem.getStoreId());
		}
		List<Food> foodInfoList = foodDao.findByStoreIdInOrderByFoodPointDesc(storeIdList);
		// ---
		for (var storeItem : storeInfoList) {
			StoreFoodVo storeAndFoodInfo = new StoreFoodVo();
			storeAndFoodInfo.setStore(storeItem);
			voList.add(storeAndFoodInfo);
			for (var foodItem : foodInfoList) {
				if (storeItem.getStoreId().equals(foodItem.getStoreId()) && foodItem.getFoodPoint() >= foodpoint) {
					storeAndFoodInfo = new StoreFoodVo();
					storeAndFoodInfo.setFood(foodItem);
					voList.add(storeAndFoodInfo);
				}
			}
		}
		res.setVoList(voList);
		return res;
	}
	// =========4.藉由評價搜尋到店家等資訊,並且排序 - API 4

//	@Override
//	public List<String> searchStorePoint(double point) {
//		// 不合資格的數字直接擋掉,避免進入資料庫
//		if (point < 1 || point > 5) {
//			return null;
//		}
//		List<String> resList = new ArrayList<>();
//		List<Store> storeList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point); // 撈到評價以上的店家,多筆資料並且排序
//		// 沒有符合要求的清單時提醒用戶
//		if (storeList.isEmpty()) {
//			return null;
//		}
//
//		for (var storeItem : storeList) {
//			List<Food> foodList = foodDao.findByStoreId(storeItem.getStoreId());
//			for (var foodItem : foodList) {
//				resList.add(" 店家 : " + storeItem.getStoreId() + " 城市 : " + storeItem.getCity() + " 店家評價 : "
//						+ storeItem.getPoint() + " 本店餐點 " + foodItem.getFoodId() + " 價格 : " + foodItem.getFoodPrice()
//						+ " 餐點評價 : " + foodItem.getFoodPoint());
//			}
//		}
//		return resList;
//	}
	/*
	 * =============================================================================
	 * ================
	 */
	// =========5.藉由店家評價找出店家and藉由菜單評價找出菜單 - API 5
//	@Override
//	public List<String> searchStorePointAndFoodPoint(double storePoint, double foodPoint) {
//		// 不合資格的數字直接擋掉,避免進入資料庫
//		if (storePoint < 1 || storePoint > 5 || foodPoint < 1 || foodPoint > 5) {
//			return null;
//		}
//
//		List<Store> storeInfoList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storePoint);
//		// 沒有符合要求的清單時提醒用戶
//		if (storeInfoList.isEmpty()) {
//			return null;
//		}
//
//		// 用於接字串
//		List<String> resList = new ArrayList<>();
//
//		// -- 掃店家,每家店只會掃一次,所以掃一次後,直接加入到字串
//		for (var storeItem : storeInfoList) {
//			resList.add("-------------------------------------------以下是符合要求的店家以及菜單");
//			// 掃一次後就加進來
//			resList.add("店家 : " + storeItem.getStoreId() + " 城市 : " + storeItem.getCity() + " 店評 : "
//					+ storeItem.getPoint());
//			// --這裡是藉由店名撈到的資料庫,並且按照評價排序
//			List<Food> foodList = foodDao.findByStoreIdOrderByFoodPointDesc(storeItem.getStoreId());
//			for (var foodItem : foodList) {
//				if (foodItem.getFoodPoint() >= foodPoint) {
//					resList.add("店家美食 : " + foodItem.getFoodId() + " 餐點價格 : " + foodItem.getFoodPrice() + " 菜評 : "
//							+ foodItem.getFoodPoint());
//				}
//			}
//		}
//		return resList;
//	}

}
