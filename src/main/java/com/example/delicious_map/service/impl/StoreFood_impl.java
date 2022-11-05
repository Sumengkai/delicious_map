package com.example.delicious_map.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Food_id;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.repository.FoodDao;
import com.example.delicious_map.repository.StoreDao;
import com.example.delicious_map.service.face.StoreFood_face;

@Service
public class StoreFood_impl implements StoreFood_face {
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodDao foodDao;

	// =========1.新增和修改Store<修改時會順便改分數,不會被覆蓋> API 1
	@Override
	public Store AddAndUpStore(String store, String city) {
		Store stor = new Store();
//		storeDao.findById(store);
		if (!StringUtils.hasText(store) || !StringUtils.hasText(city)) {
			return null;
		}

		stor.setStoreId(store);//新增
		stor.setCity(city);//新增

		double updatepoint = 0;
		List<Food> foodlist = foodDao.findByStoreId(store);// 撈出所有相對店名的foodlist
		for (var foodfor : foodlist) { // 進來時 代表是修改
			if (foodfor.getStoreid().equals(store)) {
				updatepoint += foodfor.getFoodpoint() / foodlist.size();
				String chickfoodpoint = String.valueOf(updatepoint);// 計算結果轉成字串,因為要取小數點第一位
				String stringpount = chickfoodpoint.substring(0, 3);// 抓取第幾位數以後的數字
				double saveStorePoint = Double.parseDouble(stringpount);// 再轉回double,存進去資料庫
				stor.setPoint(saveStorePoint);
				stor.setStoreId(store);
				stor.setCity(city);
			}
		}
		storeDao.save(stor);
		return stor;
	}

	// =========2.新增和修改food並且存到store的評價=========================== API 2
	@Override
	public Food AddAndUpFood(String store, String food, int price, double point) {
		Food foodvehicle = new Food();
		String chick = "\\d.\\d"; // 檢查是否符合規格
		String pointstr = String.valueOf(point);// 將double轉成字串
		boolean chickpoint = pointstr.matches(chick);// 並且確認並且回傳是否
		if (!StringUtils.hasText(store) || !StringUtils.hasText(food) || price < 0 || !chickpoint || point > 5
				|| point < 1) {
			return null;
		}
		Food_id upfood = new Food_id(store, food);// 雙主KEY
		Optional<Food> upfoodop = foodDao.findById(upfood);// 確認有沒有撞雙主KEY 有的話代表是修改
		Optional<Store> Storeop = storeDao.findById(store);
		if (upfoodop.isPresent()) {// 修改的判斷式,掉進去代表要修改
//			foodvehicle = upfoodop.get();
			foodvehicle.setStoreid(store);
			foodvehicle.setFoodid(food);
			foodvehicle.setFoodprice(price);
			foodvehicle.setFoodpoint(point);
			foodDao.save(foodvehicle);
		} else if (Storeop.isPresent()) { // 掉進去代表要新增餐點,不能新增不在店家內的餐點
			foodvehicle.setFoodid(food);
			foodvehicle.setStoreid(store);
			foodvehicle.setFoodprice(price);
			foodvehicle.setFoodpoint(point);
			foodDao.save(foodvehicle);
		} else {
			return null;
		}

		// -------//可允許新增store沒有此店家的菜單vvv(註解部分)
//		Optional<Store> Storeop = storeDao.findById(store); // 撈店家的資料庫,只會有一個店家//可允許新增store沒有此店家的菜單
//		if (!Storeop.isPresent()) {
//			return foodvehicle;
//		}
		// -------vvv-----------------------------------------藉由餐點評價分數算出平均存到店家欄位
		double somefoodpoint = 0;// 存進店家分數欄位的容器
		Store storevehicle = Storeop.get();// 取值
		List<Food> foodlist = foodDao.findByStoreId(store);// 撈出所有相對店名的資料
		for (var foodfor : foodlist) {// 編歷
			if (storevehicle.getStoreId().equals(foodfor.getStoreid())) { // 如果掉近來,表示要修改
				somefoodpoint += foodfor.getFoodpoint();// 先全部把評分加起來
			}
//			else { 可允許新增store沒有此店家的菜單要加的判斷
//				return foodvehicle; 
//			}
		}
		somefoodpoint = somefoodpoint / foodlist.size();
		String chickfoodpoint = String.valueOf(somefoodpoint);// 計算結果轉成字串,因為要取小數點第一位
		String x = chickfoodpoint.substring(0, 3);// 抓取第幾位數以後的數字
		double saveStorePoint = Double.parseDouble(x);// 再轉回double,存進去資料庫
		storevehicle.setPoint(saveStorePoint);
		storeDao.save(storevehicle);
		return foodvehicle;

	}

	// =========3.藉由城市搜尋到店家等資訊,並且限制筆數================================ API 3
	@Override
	public List<String> SearchByCity(String city, int search) {
		List<String> list = new ArrayList<>(); // 初始資料
		List<Store> citylist = storeDao.findByCity(city); // 藉由"城市"去撈store"多筆"資料
		if (citylist.size() == 0 || !StringUtils.hasText(city) || search < 0) {
			return null;
		}
		for (var cityfor : citylist) {
			// 藉由撈到store的資料庫去比對foodㄉstoreid(這裡也會有多筆)
			List<Food> foodlist = foodDao.findByStoreId(cityfor.getStoreId());
			for (var foodfor : foodlist) {
				if (cityfor.getStoreId().equals(foodfor.getStoreid())) {
					list.add("店家 : " + cityfor.getStoreId() + " 店家評價 : " + cityfor.getPoint() + " 餐點 : "
							+ foodfor.getFoodid() + " 餐點價格 : " + foodfor.getFoodprice() + " 餐點評價 : "
							+ foodfor.getFoodpoint());
				}
			}

		}
		if (search >= list.size() || search == 0) {
			return list;
		}
		List<String> reslist = list.subList(0, search);

		return reslist;
	}
	// =========4.藉由評價搜尋到店家等資訊,並且排序============================ API 4

	@Override
	public List<String> SearchStorePoint(double point) {
		List<String> reslist = new ArrayList<>();
		List<Store> storelist = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point); // 撈到評價以上的多筆資料並且排序
		for (var storefor : storelist) {
			List<Food> foodlist = foodDao.findByStoreId(storefor.getStoreId());
			for (var foodfor : foodlist) {
				if (storefor.getStoreId().equals(foodfor.getStoreid())) {
					reslist.add("店家 :" + storefor.getStoreId() + "城市 :" + storefor.getCity() + "店家評價 :"
							+ storefor.getPoint() + "本店餐點" + foodfor.getFoodid() + "價格 :" + foodfor.getFoodprice()
							+ "餐點評價 :" + foodfor.getFoodpoint());
				}
			}
		}
		return reslist;
	}
	// =========5.藉由店家評價找出店家and藉由菜單評價找出菜單======================== API 5

	@Override
	public List<String> SearchStorePointAndFoodPoint(double storepoint, double foodpoint) {
		// TODO Auto-generated method stub
		List<Store> storelist = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storepoint);
		List<Food> foodlist = foodDao.findByFoodpointGreaterThanEqualOrderByFoodpointDesc(foodpoint);
		List<String> storename = new ArrayList<>();
		for (var storefor : storelist) {// 掃店家,每家店只會掃一次
			storename.add("=============以下是符合要求的店家以及菜單");
			storename.add(// 掃一次後加進來
					"店家 : " + storefor.getStoreId() + " 城市 : " + storefor.getCity() + " 店評 : " + storefor.getPoint());
			for (var foodfor : foodlist) {// 比對完每個菜單是否符合判斷
				if (storefor.getStoreId().equals(foodfor.getStoreid())) {
					List<String> foodname = new ArrayList<>();// 這裡必須要用新的空間,不符合店名的資料
					foodname.add("店家美食 : " + foodfor.getFoodid() + " 餐點價格 : " + foodfor.getFoodprice() + " 菜評 : "
							+ foodfor.getFoodpoint());
					storename.addAll(foodname); // 每比對完一次,放進去第一個店名的list
				}

			}
		}

		return storename;
	}

}
