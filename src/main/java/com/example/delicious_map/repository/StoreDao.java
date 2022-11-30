package com.example.delicious_map.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.delicious_map.entity.Store;

@Repository
public interface StoreDao extends JpaRepository<Store,String>{
	//藉由城市找店家 api 3
	public List<Store> findByCity(String city); 
	//找出大於店家分數的資料並且遞減<高到低> api 4 5
	public List<Store> findByPointGreaterThanEqualOrderByPointDesc(double point);
	//---
	

}
