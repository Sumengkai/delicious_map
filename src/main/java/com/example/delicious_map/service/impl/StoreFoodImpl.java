package com.example.delicious_map.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.FoodId;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.repository.FoodDao;
import com.example.delicious_map.repository.StoreDao;
import com.example.delicious_map.service.face.StoreFood;

@Service
public class StoreFoodImpl implements StoreFood {
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodDao foodDao;

	// =========1.新增和修改Store<修改時會順便改分數,不會被覆蓋> API 1
	@Override
	public Store AddAndUpdateStore(String store, String city) {
		Store stor = new Store();
		if (!StringUtils.hasText(store) || !StringUtils.hasText(city)) {
			return null;
		}
		Optional<Store> storeop = storeDao.findById(store);
		if (!storeop.isPresent()) { // 新增
			stor.setStoreId(store);
			stor.setCity(city);
		} else { // 修改
			stor = storeop.get();
			stor.setCity(city);
		}
		storeDao.save(stor);
		return stor;
	}

	// =========2.新增和修改food並且存到store的評價=========================== API 2
	@Override
	public Food AddAndUpdateFood(String store, String food, int price, double point) {
		Food foodvehicle = new Food();
		String pointstr = String.valueOf(point);// 將double轉成字串
		// --------------

		// --------------
		String pointSubs = pointstr.substring(0, 3);
		double saveFoodPoint = Double.valueOf(pointSubs);
		if (!StringUtils.hasText(store) || !StringUtils.hasText(food) || price <= 0 || point > 5 || point < 1) {
			return null;
		}
		FoodId upfood = new FoodId(store, food);// 雙主KEY
		Optional<Food> upfoodop = foodDao.findById(upfood);// 確認有沒有撞雙主KEY 有的話代表是修改
		Optional<Store> Storeop = storeDao.findById(store);
		if (upfoodop.isPresent()) {// 修改的判斷式,掉進去代表要修改
			foodvehicle = upfoodop.get();
//			foodvehicle.setStoreid(store);
//			foodvehicle.setFoodid(food);
			foodvehicle.setFoodprice(price);
			foodvehicle.setFoodpoint(Math.floor(point * 10.0) / 10.0); // 轉型過後的double
			foodDao.save(foodvehicle);
		} else if (Storeop.isPresent()) { // 掉進去代表要新增餐點,且不能新增不在店家內的餐點
			foodvehicle.setFoodid(food);
			foodvehicle.setStoreid(store);
			foodvehicle.setFoodprice(price);
			foodvehicle.setFoodpoint(Math.floor(point * 10.0) / 10.0);// 轉型過後的double
			foodDao.save(foodvehicle);
		} else {
			return null;
		}
		// -------vvv-----------------------------------------藉由餐點評價分數算出平均存到店家欄位
		double somefoodpoint = 0;// 存進店家分數欄位的容器
		Store storevehicle = Storeop.get();// 取值
		List<Food> foodlist = foodDao.findByStoreId(store);// 撈出所有相對店名的資料
		for (var foodfor : foodlist) {// 編歷
			somefoodpoint += foodfor.getFoodpoint();// 先全部把評分加起來

		}
		somefoodpoint = somefoodpoint / foodlist.size();
		storevehicle.setPoint(Math.floor(somefoodpoint * 10.0) / 10.0);
		storeDao.save(storevehicle);
		return foodvehicle;

	}

	// =========3.藉由城市搜尋到店家等資訊,並且限制筆數================================ API 3
	@Override
	public List<String> SearchByCity(String city, int search) {
		List<String> list = new ArrayList<>(); // 初始資料
		List<Store> citylist = storeDao.findByCity(city); // 藉由"城市"去撈store"多筆"資料
		if (citylist.size() == 0 || !StringUtils.hasText(city)) {
			return null;
		}
		for (var cityfor : citylist) {
			// 藉由撈到store的資料庫去比對foodㄉstoreid(這裡也會有多筆)
			List<Food> foodlist = foodDao.findByStoreId(cityfor.getStoreId());
			for (var foodfor : foodlist) {
				list.add("店家 : " + cityfor.getStoreId() + " 店家評價 : " + cityfor.getPoint() + " 餐點 : "
						+ foodfor.getFoodid() + " 餐點價格 : " + foodfor.getFoodprice() + " 餐點評價 : "
						+ foodfor.getFoodpoint());
			}

		}
		if (search <= 0) {
			return list;
		}
		return list.subList(0, search);
	}
	// =========4.藉由評價搜尋到店家等資訊,並且排序============================ API 4

	@Override
	public List<String> SearchStorePoint(double point) {
		List<String> reslist = new ArrayList<>();
		List<Store> storelist = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point); // 撈到評價以上的多筆資料並且排序
		for (var storefor : storelist) {
			List<Food> foodlist = foodDao.findByStoreId(storefor.getStoreId());
			for (var foodfor : foodlist) {
				reslist.add(" 店家 : " + storefor.getStoreId() + " 城市 : " + storefor.getCity() + " 店家評價 : "
						+ storefor.getPoint() + " 本店餐點 " + foodfor.getFoodid() + " 價格 : " + foodfor.getFoodprice()
						+ " 餐點評價 : " + foodfor.getFoodpoint());
			}
		}
		return reslist;
	}
	// =========5.藉由店家評價找出店家and藉由菜單評價找出菜單======================== API 5

	@Override
	public List<String> SearchStorePointAndFoodPoint(double storepoint, double foodpoint) {
		List<Store> storelist = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storepoint);
//		List<Food> foodlist = foodDao.findByFoodpointGreaterThanEqualOrderByFoodpointDesc(foodpoint); //會撈過多ㄉ資料
		List<String> storename = new ArrayList<>();
		for (var storefor : storelist) {// 掃店家,每家店只會掃一次
			storename.add("-------------------------------------------以下是符合要求的店家以及菜單");
			storename.add(// 掃一次後加進來
					"店家 : " + storefor.getStoreId() + " 城市 : " + storefor.getCity() + " 店評 : " + storefor.getPoint());
			List<Food> foodlist = foodDao.findByStoreIdOrderByFoodpointDesc(storefor.getStoreId()); //撈資料的同時比對店家
			for (var foodfor : foodlist) {// 比對完每個菜單是否符合判斷
//*				if (storefor.getStoreId().equals(foodfor.getStoreid())) {
				if (foodfor.getFoodpoint() >= foodpoint) {
					List<String> foodname = new ArrayList<>();// 這裡必須要用新的空間,不然會放進不符合店名的資料
					foodname.add("店家美食 : " + foodfor.getFoodid() + " 餐點價格 : " + foodfor.getFoodprice() + " 菜評 : "
							+ foodfor.getFoodpoint());
					storename.addAll(foodname); // 每比對完一次,放進去第一個店名的list
//*				}
				}
			}
		}

		return storename;
	}

}
