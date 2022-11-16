package com.example.delicious_map.service.face;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.vo.StoreFoodRes;
import com.example.delicious_map.vo.StoreRes;

public interface StoreFood {

	// 針對<店家>新增.修改
	public StoreRes addAndUpdateStore(String id, String city);


	// <餐點>新增.修改並且存進 <店家> 的評價
	public StoreRes addAndUpdateFood(String store, String food, Integer price, double point);


	// 藉由<城市>找出<店家>以及相對應的<餐點>... <並且可以限制搜尋筆數>
	public List<String> searchByCityAndLimit(String city, int limitNumber);


	// 藉由評價找出店家以及相對應的資訊... 搜尋評價 //並且要排序
	public List<String> searchStorePoint(double point);


	// 藉由<店家評價>找出<店家>藉由<菜單評價>找出<菜單> //並且兩個都要排序
	public List<String> searchStorePointAndFoodPoint(double storepoint, double foodpoint);

}
