package com.example.delicious_map.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.Store;
import com.example.delicious_map.repository.StoreDao;
import com.example.delicious_map.service.face.Store_face;

@Service
public class Store_impl implements Store_face{
	@Autowired
	private StoreDao storeDao;

	// == 1.�W�[�M�ק�
	public Store addandupdatestore(String store, String city) {
		Store add = new Store();
		storeDao.findById(store);
		if (!StringUtils.hasText(store)) {
			return null;
		}
		// �s�W�M�ק�
		add.setStore_id(store);
		add.setCity(city);
		storeDao.save(add);
		return add;

	}

	// 2.==�R��
	public Store delete(String store) {
		Store delete = new Store();
		if (!StringUtils.hasText(store)) {
			return null;
		}
		Optional<Store> menuop = storeDao.findById(store);
		if (!menuop.isPresent()) {

			return null;

		} else {
			storeDao.deleteById(store);
		}
		return delete;
	}

	// 3.�d�߫���==
	public List<Store> find(String city) {
		List<Store> store = storeDao.findByCity(city);
		return store;
	}

	// 4.�d�ߩ��a==
	public Store findstore(String store) {
		Optional<Store> op = storeDao.findById(store);
		if (!op.isPresent() || !StringUtils.hasText(store)) {
			return null;
		}
		Store add = new Store();
		add=op.get();
		return add;

	}

}
