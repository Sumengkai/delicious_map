package com.example.delicious_map.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.delicious_map.entity.Store;

@Repository
public interface StoreDao extends JpaRepository<Store,String>{
	//�ǥѫ����䩱�a api 3
	public List<Store> findByCity(String city); 
	//��X�j�󩱮a���ƪ���ƨåB����<����C> api 4 5
	public List<Store> findByPointGreaterThanEqualOrderByPointDesc(double point);
	//---
	

}
