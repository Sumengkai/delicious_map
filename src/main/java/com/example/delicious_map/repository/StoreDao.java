package com.example.delicious_map.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.delicious_map.entity.MenuMenu;
import com.example.delicious_map.entity.Store;
@Repository
@Transactional
public interface StoreDao extends JpaRepository<Store,String>{
	public List<Store> findAllByCity(String city) ;
	public List<Store> findByCity(String city); 
//	public List<MenuMenu> findByCityEquals(String city);
//	public List<Store> findByCityEquals(String city);
}
