package com.example.delicious_map.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.delicious_map.entity.MenuMenu;
import com.example.delicious_map.entity.Store;
@Repository
public interface Menudao extends JpaRepository<MenuMenu,String>{
	public MenuMenu findByStoreid(String s);
	public List<MenuMenu> findByEvaluationGreaterThanEqual(int evaluation);
	public List<MenuMenu> findByEvaluationGreaterThanAndStoreid(int evaluation,String Storeid);
}
