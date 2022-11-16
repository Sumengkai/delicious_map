package com.example.delicious_map.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.example.delicious_map.vo.StoreFoodRes;
import com.example.delicious_map.vo.StoreRes;

@Service
public class StoreFoodImpl implements StoreFood {
//----------------------------	
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodDao foodDao;
// ---------------------------

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

		// 掉進去代表要新增,cuz找不到主key
		if (!storeOp.isPresent()) {
			storeInfo.setStoreId(store);
			storeInfo.setCity(city);
			storeDao.save(storeInfo);
			return new StoreRes(storeInfo, StoreFood_RtnCode.ADDSUCCESSFUL.getMessage());
		}

		// 上面沒return掉代表就是要修改
		storeInfo = storeOp.get();
		storeInfo.setCity(city);
		storeDao.save(storeInfo);
		return new StoreRes(storeInfo, StoreFood_RtnCode.UPDATESUCCESSFUL.getMessage());
	}

	// =========2.新增和修改Food並且存到store的評價 - API 2
	@Override
	public StoreRes addAndUpdateFood(String store, String food, Integer price, double point) {
		Food foodInfo = new Food();
		StoreRes res = new StoreRes();
		// -----防呆內容
		if (!StringUtils.hasText(store) || !StringUtils.hasText(food)) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_KEY.getCode(), StoreFood_RtnCode.CANT_FIND_KEY.getMessage());
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

		// 用於確認新增或修改的布林值,回傳不同的訊息給用戶
		boolean addOrUpdate = false;

		FoodId upFood = new FoodId(store, food); // 雙主KEY

		// 確認有沒有撞雙主KEY 有的話代表是修改
		Optional<Food> updateFoodOp = foodDao.findById(upFood);

		// 不能新增不在店家內的餐點要撈ㄉ資料
		Optional<Store> StoreOp = storeDao.findById(store);

		if (updateFoodOp.isPresent()) { // 修改的判斷式,掉進去代表要修改餐點資訊
			foodInfo = updateFoodOp.get();
			foodInfo.setFoodprice(price);
			foodInfo.setFoodpoint(Math.floor(point * 10.0) / 10.0); // 無條件捨去至小數點後 1 位
			foodDao.save(foodInfo);
		} else if (StoreOp.isPresent()) { // 掉進去代表要新增餐點,"且"不能新增不在店家內的餐點
			foodInfo.setFoodid(food);
			foodInfo.setStoreid(store);
			foodInfo.setFoodprice(price);
			foodInfo.setFoodpoint(Math.floor(point * 10.0) / 10.0);
			foodDao.save(foodInfo);
			addOrUpdate = true; // "新增"的布林
		} else {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_REGULATION.getCode(),
					StoreFood_RtnCode.CANT_FIND_REGULATION.getMessage()); // 不能新增不在店家內的餐點的訊息
			return res;
		}

		// ------------------------------------------------藉由餐點評價分數算出平均存到店家欄位

		// 存進店家分數欄位的容器
		double someFoodPoint = 0;

		Store storeInfo = StoreOp.get();

		// 撈出所有相對店名的資料
		List<Food> foodList = foodDao.findByStoreId(store);

		// 先全部把評分加起來
		for (var foodfor : foodList) {
			someFoodPoint += foodfor.getFoodpoint();
		}
		// 同家店有多少餐點就有幾個size
		someFoodPoint = someFoodPoint / foodList.size();

		// 無條件捨去至小數點後 1 位
		storeInfo.setPoint(Math.floor(someFoodPoint * 10.0) / 10.0);
		storeDao.save(storeInfo);

		// 根據修改或新增給出不同訊息
		if (addOrUpdate) {
			return new StoreRes(foodInfo, StoreFood_RtnCode.ADDSUCCESSFUL.getMessage()); // 新增訊息
		}
		return new StoreRes(foodInfo, StoreFood_RtnCode.UPDATESUCCESSFUL.getMessage()); // 修改訊息

	}

	// =========3.藉由城市搜尋到店家等資訊,並且限制筆數 - API 3
	@Override
	public List<String> searchByCityAndLimit(String city, int limitNumber) {
		if (!StringUtils.hasText(city)) {
			return null;
		}
		// 用於接字串
		List<String> resList = new ArrayList<>();

		// 藉由 城市 去撈店家資料
		List<Store> cityList = storeDao.findByCity(city);

		// 沒有符合要求的清單時提醒用戶
		if (cityList.isEmpty()) {
			return null;
		}

		for (var cityItem : cityList) {
			// 藉由撈到store的資料庫去比對foodㄉstoreid(這裡會有多筆)
			List<Food> foodList = foodDao.findByStoreId(cityItem.getStoreId());
			for (var foodItem : foodList) {
				resList.add("店家 : " + cityItem.getStoreId() + " 店家評價 : " + cityItem.getPoint() + " 餐點 : "
						+ foodItem.getFoodid() + " 餐點價格 : " + foodItem.getFoodprice() + " 餐點評價 : "
						+ foodItem.getFoodpoint());
			}

		}

		// 當limitNumber小於零或是空時 亦或是 limitNumber大於我的list , 回傳整個list
		if (limitNumber <= 0 || limitNumber > resList.size()) {
			return resList;
		}

		return resList.subList(0, limitNumber);
	}

	// =========4.藉由評價搜尋到店家等資訊,並且排序 - API 4

	@Override
	public List<String> searchStorePoint(double point) {
		// 不合資格的數字直接擋掉,避免進入資料庫
		if (point < 1 || point > 5) {
			return null;
		}
		List<String> resList = new ArrayList<>();
		List<Store> storeList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point); // 撈到評價以上的店家,多筆資料並且排序
		// 沒有符合要求的清單時提醒用戶
		if (storeList.isEmpty()) {
			return null;
		}

		for (var storeItem : storeList) {
			List<Food> foodList = foodDao.findByStoreId(storeItem.getStoreId());
			for (var foodItem : foodList) {
				resList.add(" 店家 : " + storeItem.getStoreId() + " 城市 : " + storeItem.getCity() + " 店家評價 : "
						+ storeItem.getPoint() + " 本店餐點 " + foodItem.getFoodid() + " 價格 : " + foodItem.getFoodprice()
						+ " 餐點評價 : " + foodItem.getFoodpoint());
			}
		}
		return resList;
	}

	// =========5.藉由店家評價找出店家and藉由菜單評價找出菜單 - API 5
	@Override
	public List<String> searchStorePointAndFoodPoint(double storePoint, double foodPoint) {
		// 不合資格的數字直接擋掉,避免進入資料庫
		if (storePoint < 1 || storePoint > 5 || foodPoint < 1 || foodPoint > 5) {
			return null;
		}

		List<Store> storeList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storePoint);
		// 沒有符合要求的清單時提醒用戶
		if (storeList.isEmpty()) {
			return null;
		}

		// 用於接字串
		List<String> resList = new ArrayList<>();

		// -- 掃店家,每家店只會掃一次,所以掃一次後,直接加入到字串
		for (var storeItem : storeList) {
			resList.add("-------------------------------------------以下是符合要求的店家以及菜單");
			// 掃一次後就加進來
			resList.add(
					"店家 : " + storeItem.getStoreId() + " 城市 : " + storeItem.getCity() + " 店評 : " + storeItem.getPoint());
			// --這裡是藉由店名撈到的資料庫,並且按照評價排序
			List<Food> foodList = foodDao.findByStoreIdOrderByFoodpointDesc(storeItem.getStoreId());
			for (var foodItem : foodList) {
				if (foodItem.getFoodpoint() >= foodPoint) {
					resList.add("店家美食 : " + foodItem.getFoodid() + " 餐點價格 : " + foodItem.getFoodprice() + " 菜評 : "
							+ foodItem.getFoodpoint());
				}
			}
		}
		return resList;
	}

}
