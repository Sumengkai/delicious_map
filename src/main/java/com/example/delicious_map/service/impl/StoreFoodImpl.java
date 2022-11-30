package com.example.delicious_map.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.delicious_map.Constances.StoreFood_RtnCode;
import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.FoodId;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.repository.FoodDao;
import com.example.delicious_map.repository.StoreDao;
import com.example.delicious_map.service.face.StoreFood;
import com.example.delicious_map.vo.StoreFoodVo;
import com.example.delicious_map.vo.StoreRes;

@Service
public class StoreFoodImpl implements StoreFood {
//----------------------------	
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodDao foodDao;

// ---------------------------
	private StoreRes checkInfo(String store, String food, Integer price, double point) {
		StoreRes res = new StoreRes();
		if (!StringUtils.hasText(store) || !StringUtils.hasText(food)) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STOREORFOOD.getCode(),
					StoreFood_RtnCode.CANT_FIND_STOREORFOOD.getMessage());
			return res;
		}

		if (price == null || price < 0) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_PRICE.getCode(), StoreFood_RtnCode.CANT_FIND_PRICE.getMessage());
			return res;
		}

		if (point > 5 || point < 1) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_POINT.getCode(), StoreFood_RtnCode.CANT_FIND_POINT.getMessage());
			return res;
		}
		return null;
	}

	// =========1.�s�W�M�ק�Store - API 1
	@Override
	public StoreRes addAndUpdateStore(String store, String city) {
		Store storeInfo = new Store();
		StoreRes res = new StoreRes();
		if (!StringUtils.hasText(store) || !StringUtils.hasText(city)) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORE.getCode(), StoreFood_RtnCode.CANT_FIND_STORE.getMessage()); // ���Ū�
			return res;
		}
		Optional<Store> storeOp = storeDao.findById(store);

		// ���i�h�N��n�s�W,�]���䤣��Dkey
		if (!storeOp.isPresent()) {
			storeInfo.setStoreId(store);
			storeInfo.setCity(city);
			storeDao.save(storeInfo);
			return new StoreRes(storeInfo, StoreFood_RtnCode.ADD_SUCCESSFUL.getMessage());
		}

		// �W���Sreturn���N��N�O�n�ק�
		storeInfo = storeOp.get();
		storeInfo.setCity(city);
		storeDao.save(storeInfo);
		return new StoreRes(storeInfo, StoreFood_RtnCode.UPDATE_SUCCESSFUL.getMessage());
	}

	// =========2.�s�W�M�ק�Food�åB�s��store������ - API 2
	@Override
	public StoreRes addAndUpdateFood(String store, String food, Integer price, double point) {
		Food foodInfo = new Food();
		// -----���b���e
		StoreRes res = checkInfo(store, food, price, point);
		if (res != null) {
			return res;
		}

		// �Ω�T�{�s�W�έק諸���L��,�^�Ǥ��P���T�����Τ�
		boolean addOrUpdate = false;

		FoodId foodId = new FoodId(store, food); // ���DKEY

		// �T�{���S�������DKEY �����ܥN��O�ק�
		Optional<Food> foodOp = foodDao.findById(foodId);

		// ����s�W���b���a�����\�I�n�������
		Optional<Store> storeOp = storeDao.findById(store);

		if (foodOp.isPresent()) { // �ק諸�P�_��,���i�h�N��n�ק��\�I��T
			foodInfo = foodOp.get();
			foodInfo.setFoodPrice(price);
			foodInfo.setFoodPoint(Math.floor(point * 10.0) / 10.0); // �L����˥h�ܤp���I�� 1 ��
			foodDao.save(foodInfo);
		} else if (storeOp.isPresent()) { // ���i�h�N��n�s�W�\�I,"�B"����s�W���b���a�����\�I
			foodInfo.setFoodId(food);
			foodInfo.setStoreId(store);
			foodInfo.setFoodPrice(price);
			foodInfo.setFoodPoint(Math.floor(point * 10.0) / 10.0);
			foodDao.save(foodInfo);
			addOrUpdate = true; // "�s�W"�����L
		} else {
			res = new StoreRes();
			res.setMessage(StoreFood_RtnCode.CANT_FIND_REGULATION_STORE.getCode(),
					StoreFood_RtnCode.CANT_FIND_REGULATION_STORE.getMessage()); // ����s�W���b���a�����\�I���T��
			return res;
		}

		// ------------------------------------------------�ǥ��\�I�������ƺ�X�����s�쩱�a���

		// �s�i���a������쪺�e��
		double someFoodPoint = 0;

		Store storeInfo = storeOp.get();

		// ���X�Ҧ��۹况�W�����
		List<Food> storeList = foodDao.findByStoreId(store);

		// ������������[�_��
		for (var foodfor : storeList) {
			someFoodPoint += foodfor.getFoodPoint();
		}
		// �P�a�����h���\�I�N���X��size
		someFoodPoint = someFoodPoint / storeList.size();

		// �L����˥h�ܤp���I�� 1 ��
		storeInfo.setPoint(Math.floor(someFoodPoint * 10.0) / 10.0);
		storeDao.save(storeInfo);

		// �ھڭק�ηs�W���X���P�T��
		if (addOrUpdate) {
			return new StoreRes(foodInfo, StoreFood_RtnCode.ADD_SUCCESSFUL.getMessage()); // �s�W�T��
		}
		return new StoreRes(foodInfo, StoreFood_RtnCode.UPDATE_SUCCESSFUL.getMessage()); // �ק�T��

	}

	// =========3.�ǥѫ����j�M�쩱�a����T,�åB����� - API 3
	@Override
	public StoreRes searchByCityAndLimit(String city, int limitNumber) {
		StoreRes res = new StoreRes();
		if (!StringUtils.hasText(city)) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_PARAMETER_CITY.getCode(),
					StoreFood_RtnCode.CANT_FIND_PARAMETER_CITY.getMessage()); // ���Ū�
			return res;
		}
		List<StoreFoodVo> voList = new ArrayList<>();

		List<Store> storeInfoList = storeDao.findByCity(city);
		if (storeInfoList.isEmpty()) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_CITYLIST.getCode(),
					StoreFood_RtnCode.CANT_FIND_CITYLIST.getMessage());
			return res;
		}

		List<String> storeIdList = new ArrayList<>();
		for (var storeIdItem : storeInfoList) {
			storeIdList.add(storeIdItem.getStoreId());
		}
		List<Food> foodInfoList = foodDao.findByStoreIdIn(storeIdList);
		// �ݥ��]food�~�h���j��,�]���ڻݭn�N��������T�H�Ω��a����T�s�b�P�@��list�Papi4/5���P,�]���̫�ݭn����list
		for (var foodItem : foodInfoList) {
			for (var storeItem : storeInfoList) {
				if (foodItem.getStoreId().equals(storeItem.getStoreId())) {
					StoreFoodVo storeAndFoodInfo = new StoreFoodVo();
					storeAndFoodInfo.setStore(storeItem);
					storeAndFoodInfo.setFood(foodItem);
					voList.add(storeAndFoodInfo);
				}
			}
		}
		
		// ��limitNumber�p��s�άO�Ů� ��άO limitNumber�j��ڪ�list , �^�Ǿ��list
		if (limitNumber <= 0 || limitNumber > voList.size()) {
			res.setVoList(voList);
			return res;
		}
		// �W���Sreturn���N��ݭn��list
		res.setVoList(voList.subList(0, limitNumber));
		return res;
	}

	// =========4.�ǥѵ����j�M�쩱�a����T,�åB�Ƨ� - API 4(�ĤG����)
	@Override
	public StoreRes searchStorePoint(double point) {
		StoreRes res = new StoreRes();
		if (point < 1 || point > 5) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getCode(),
					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
			return res;
		}
		List<StoreFoodVo> voList = new ArrayList<>();
		List<String> storeIdList = new ArrayList<>();
		List<Store> storeInfoList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point);
		if (storeInfoList.isEmpty()) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getCode(),
					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
			return res;
		}
		for (var storeItem : storeInfoList) {
			storeIdList.add(storeItem.getStoreId());
		}
		List<Food> foodInfoList = foodDao.findByStoreIdIn(storeIdList);
		for (var storeItem : storeInfoList) {
			StoreFoodVo storeAndFoodInfo = new StoreFoodVo();
			storeAndFoodInfo.setStore(storeItem);
			voList.add(storeAndFoodInfo);
			for (var foodItem : foodInfoList) {
				if (storeItem.getStoreId().equals(foodItem.getStoreId())) {
					storeAndFoodInfo = new StoreFoodVo();
					storeAndFoodInfo.setFood(foodItem);
					voList.add(storeAndFoodInfo);
				}
			}
		}
		res.setVoList(voList);
		return res;
	}

	// =========5.�ǥѩ��a������X���aand�ǥѵ�������X��� - API 5(�ĤG����)
	@Override
	public StoreRes searchStorePointAndFoodPoint(double storepoint, double foodpoint) {
		StoreRes res = new StoreRes();
		if (storepoint < 1 || storepoint > 5 || foodpoint < 1 || foodpoint > 5) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage(),
					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
			return res;
		}
		List<StoreFoodVo> voList = new ArrayList<>();

		List<Store> storeInfoList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storepoint);
		if (storeInfoList.isEmpty()) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage(),
					StoreFood_RtnCode.CANT_FIND_STORELIST_OR_FOODLIST.getMessage());
			return res;
		}
		List<String> storeIdList = new ArrayList<>();
		for (var storeItem : storeInfoList) {
			storeIdList.add(storeItem.getStoreId());
		}
		List<Food> foodInfoList = foodDao.findByStoreIdInOrderByFoodPointDesc(storeIdList);
		// ---
		for (var storeItem : storeInfoList) {
			StoreFoodVo storeAndFoodInfo = new StoreFoodVo();
			storeAndFoodInfo.setStore(storeItem);
			voList.add(storeAndFoodInfo);
			for (var foodItem : foodInfoList) {
				if (storeItem.getStoreId().equals(foodItem.getStoreId()) && foodItem.getFoodPoint() >= foodpoint) {
					storeAndFoodInfo = new StoreFoodVo();
					storeAndFoodInfo.setFood(foodItem);
					voList.add(storeAndFoodInfo);
				}
			}
		}
		res.setVoList(voList);
		return res;
	}
	// =========4.�ǥѵ����j�M�쩱�a����T,�åB�Ƨ� - API 4

//	@Override
//	public List<String> searchStorePoint(double point) {
//		// ���X��檺�Ʀr�����ױ�,�קK�i�J��Ʈw
//		if (point < 1 || point > 5) {
//			return null;
//		}
//		List<String> resList = new ArrayList<>();
//		List<Store> storeList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point); // ��������H�W�����a,�h����ƨåB�Ƨ�
//		// �S���ŦX�n�D���M��ɴ����Τ�
//		if (storeList.isEmpty()) {
//			return null;
//		}
//
//		for (var storeItem : storeList) {
//			List<Food> foodList = foodDao.findByStoreId(storeItem.getStoreId());
//			for (var foodItem : foodList) {
//				resList.add(" ���a : " + storeItem.getStoreId() + " ���� : " + storeItem.getCity() + " ���a���� : "
//						+ storeItem.getPoint() + " �����\�I " + foodItem.getFoodId() + " ���� : " + foodItem.getFoodPrice()
//						+ " �\�I���� : " + foodItem.getFoodPoint());
//			}
//		}
//		return resList;
//	}
	/*
	 * =============================================================================
	 * ================
	 */
	// =========5.�ǥѩ��a������X���aand�ǥѵ�������X��� - API 5
//	@Override
//	public List<String> searchStorePointAndFoodPoint(double storePoint, double foodPoint) {
//		// ���X��檺�Ʀr�����ױ�,�קK�i�J��Ʈw
//		if (storePoint < 1 || storePoint > 5 || foodPoint < 1 || foodPoint > 5) {
//			return null;
//		}
//
//		List<Store> storeInfoList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storePoint);
//		// �S���ŦX�n�D���M��ɴ����Τ�
//		if (storeInfoList.isEmpty()) {
//			return null;
//		}
//
//		// �Ω󱵦r��
//		List<String> resList = new ArrayList<>();
//
//		// -- �����a,�C�a���u�|���@��,�ҥH���@����,�����[�J��r��
//		for (var storeItem : storeInfoList) {
//			resList.add("-------------------------------------------�H�U�O�ŦX�n�D�����a�H�ε��");
//			// ���@����N�[�i��
//			resList.add("���a : " + storeItem.getStoreId() + " ���� : " + storeItem.getCity() + " ���� : "
//					+ storeItem.getPoint());
//			// --�o�̬O�ǥѩ��W���쪺��Ʈw,�åB���ӵ����Ƨ�
//			List<Food> foodList = foodDao.findByStoreIdOrderByFoodPointDesc(storeItem.getStoreId());
//			for (var foodItem : foodList) {
//				if (foodItem.getFoodPoint() >= foodPoint) {
//					resList.add("���a���� : " + foodItem.getFoodId() + " �\�I���� : " + foodItem.getFoodPrice() + " ��� : "
//							+ foodItem.getFoodPoint());
//				}
//			}
//		}
//		return resList;
//	}

}
