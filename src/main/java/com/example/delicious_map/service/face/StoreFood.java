package com.example.delicious_map.service.face;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.vo.StoreRes;

public interface StoreFood {
	//------------------------------------------------------1
	// 針對store新增.修改
	public StoreRes AddAndUpdateStore(String id, String city);

//------------------------------------------------------2

	// food新增修改並且存進store的評價
	public Food AddAndUpdateFood(String store, String food, int price, double point);

//------------------------------------------------------3
	// 藉由城市找出店家以及相對應的餐點... 搜尋筆數
	public List<String> SearchByCity(String city, int num);

// ------------------------------------------------------4
	// 藉由評價找出店家以及相對應的資訊... 搜尋評價 //並且要排序
	public List<String> SearchStorePoint(double point);

//-------------------------------------------------------5
	//藉由店家評價找出店家and藉由菜單評價找出菜單 //並且兩個都要排序
	public List<String> SearchStorePointAndFoodPoint(double storepoint, double foodpoint);

}
