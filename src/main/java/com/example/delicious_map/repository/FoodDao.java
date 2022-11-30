package com.example.delicious_map.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.FoodId;
//import com.example.delicious_map.entity.Store;
import com.example.delicious_map.entity.Store;

@Repository
public interface FoodDao extends JpaRepository<Food,FoodId>{
	public List<Food> findByStoreId(String store); //api 2
	public List<Food>findByStoreIdIn(List<String> list); //api 4
	public List<Food> findByFoodPointGreaterThanEqualOrderByFoodPointDesc(double foodpoint);//5 api
	//----------------
	public List<Food> findByStoreIdOrderByFoodPointDesc(String store); //5 api
	//-------------
	public List<Food> findByStoreIdInOrderByFoodPointDesc(List<String> list); //5 api
}
