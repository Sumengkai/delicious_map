package com.example.delicious_map.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Food_id;
//import com.example.delicious_map.entity.Store;
import com.example.delicious_map.entity.Store;

@Repository
public interface FoodDao extends JpaRepository<Food,Food_id>{
	public List<Food> findByStoreId(String store);
	public List<Food> findByFoodpointGreaterThanEqualOrderByFoodpointDesc(double foodpoint);
}
