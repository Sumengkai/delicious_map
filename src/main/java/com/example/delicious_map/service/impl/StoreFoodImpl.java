package com.example.delicious_map.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.example.delicious_map.vo.StoreFoodRes;
import com.example.delicious_map.vo.StoreRes;

@Service
public class StoreFoodImpl implements StoreFood {
//----------------------------	
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodDao foodDao;
// ---------------------------

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

		// ���i�h�N��n�s�W,cuz�䤣��Dkey
		if (!storeOp.isPresent()) {
			storeInfo.setStoreId(store);
			storeInfo.setCity(city);
			storeDao.save(storeInfo);
			return new StoreRes(storeInfo, StoreFood_RtnCode.ADDSUCCESSFUL.getMessage());
		}

		// �W���Sreturn���N��N�O�n�ק�
		storeInfo = storeOp.get();
		storeInfo.setCity(city);
		storeDao.save(storeInfo);
		return new StoreRes(storeInfo, StoreFood_RtnCode.UPDATESUCCESSFUL.getMessage());
	}

	// =========2.�s�W�M�ק�Food�åB�s��store������ - API 2
	@Override
	public StoreRes addAndUpdateFood(String store, String food, Integer price, double point) {
		Food foodInfo = new Food();
		StoreRes res = new StoreRes();
		// -----���b���e
		if (!StringUtils.hasText(store) || !StringUtils.hasText(food)) {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_KEY.getCode(), StoreFood_RtnCode.CANT_FIND_KEY.getMessage());
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

		// �Ω�T�{�s�W�έק諸���L��,�^�Ǥ��P���T�����Τ�
		boolean addOrUpdate = false;

		FoodId upFood = new FoodId(store, food); // ���DKEY

		// �T�{���S�������DKEY �����ܥN��O�ק�
		Optional<Food> updateFoodOp = foodDao.findById(upFood);

		// ����s�W���b���a�����\�I�n���x���
		Optional<Store> StoreOp = storeDao.findById(store);

		if (updateFoodOp.isPresent()) { // �ק諸�P�_��,���i�h�N��n�ק��\�I��T
			foodInfo = updateFoodOp.get();
			foodInfo.setFoodprice(price);
			foodInfo.setFoodpoint(Math.floor(point * 10.0) / 10.0); // �L����˥h�ܤp���I�� 1 ��
			foodDao.save(foodInfo);
		} else if (StoreOp.isPresent()) { // ���i�h�N��n�s�W�\�I,"�B"����s�W���b���a�����\�I
			foodInfo.setFoodid(food);
			foodInfo.setStoreid(store);
			foodInfo.setFoodprice(price);
			foodInfo.setFoodpoint(Math.floor(point * 10.0) / 10.0);
			foodDao.save(foodInfo);
			addOrUpdate = true; // "�s�W"�����L
		} else {
			res.setMessage(StoreFood_RtnCode.CANT_FIND_REGULATION.getCode(),
					StoreFood_RtnCode.CANT_FIND_REGULATION.getMessage()); // ����s�W���b���a�����\�I���T��
			return res;
		}

		// ------------------------------------------------�ǥ��\�I�������ƺ�X�����s�쩱�a���

		// �s�i���a������쪺�e��
		double someFoodPoint = 0;

		Store storeInfo = StoreOp.get();

		// ���X�Ҧ��۹况�W�����
		List<Food> foodList = foodDao.findByStoreId(store);

		// ������������[�_��
		for (var foodfor : foodList) {
			someFoodPoint += foodfor.getFoodpoint();
		}
		// �P�a�����h���\�I�N���X��size
		someFoodPoint = someFoodPoint / foodList.size();

		// �L����˥h�ܤp���I�� 1 ��
		storeInfo.setPoint(Math.floor(someFoodPoint * 10.0) / 10.0);
		storeDao.save(storeInfo);

		// �ھڭק�ηs�W���X���P�T��
		if (addOrUpdate) {
			return new StoreRes(foodInfo, StoreFood_RtnCode.ADDSUCCESSFUL.getMessage()); // �s�W�T��
		}
		return new StoreRes(foodInfo, StoreFood_RtnCode.UPDATESUCCESSFUL.getMessage()); // �ק�T��

	}

	// =========3.�ǥѫ����j�M�쩱�a����T,�åB����� - API 3
	@Override
	public List<String> searchByCityAndLimit(String city, int limitNumber) {
		if (!StringUtils.hasText(city)) {
			return null;
		}
		// �Ω󱵦r��
		List<String> resList = new ArrayList<>();

		// �ǥ� ���� �h�����a���
		List<Store> cityList = storeDao.findByCity(city);

		// �S���ŦX�n�D���M��ɴ����Τ�
		if (cityList.isEmpty()) {
			return null;
		}

		for (var cityItem : cityList) {
			// �ǥѼ���store����Ʈw�h���food�xstoreid(�o�̷|���h��)
			List<Food> foodList = foodDao.findByStoreId(cityItem.getStoreId());
			for (var foodItem : foodList) {
				resList.add("���a : " + cityItem.getStoreId() + " ���a���� : " + cityItem.getPoint() + " �\�I : "
						+ foodItem.getFoodid() + " �\�I���� : " + foodItem.getFoodprice() + " �\�I���� : "
						+ foodItem.getFoodpoint());
			}

		}

		// ��limitNumber�p��s�άO�Ů� ��άO limitNumber�j��ڪ�list , �^�Ǿ��list
		if (limitNumber <= 0 || limitNumber > resList.size()) {
			return resList;
		}

		return resList.subList(0, limitNumber);
	}

	// =========4.�ǥѵ����j�M�쩱�a����T,�åB�Ƨ� - API 4

	@Override
	public List<String> searchStorePoint(double point) {
		// ���X��檺�Ʀr�����ױ�,�קK�i�J��Ʈw
		if (point < 1 || point > 5) {
			return null;
		}
		List<String> resList = new ArrayList<>();
		List<Store> storeList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point); // ��������H�W�����a,�h����ƨåB�Ƨ�
		// �S���ŦX�n�D���M��ɴ����Τ�
		if (storeList.isEmpty()) {
			return null;
		}

		for (var storeItem : storeList) {
			List<Food> foodList = foodDao.findByStoreId(storeItem.getStoreId());
			for (var foodItem : foodList) {
				resList.add(" ���a : " + storeItem.getStoreId() + " ���� : " + storeItem.getCity() + " ���a���� : "
						+ storeItem.getPoint() + " �����\�I " + foodItem.getFoodid() + " ���� : " + foodItem.getFoodprice()
						+ " �\�I���� : " + foodItem.getFoodpoint());
			}
		}
		return resList;
	}

	// =========5.�ǥѩ��a������X���aand�ǥѵ�������X��� - API 5
	@Override
	public List<String> searchStorePointAndFoodPoint(double storePoint, double foodPoint) {
		// ���X��檺�Ʀr�����ױ�,�קK�i�J��Ʈw
		if (storePoint < 1 || storePoint > 5 || foodPoint < 1 || foodPoint > 5) {
			return null;
		}

		List<Store> storeList = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storePoint);
		// �S���ŦX�n�D���M��ɴ����Τ�
		if (storeList.isEmpty()) {
			return null;
		}

		// �Ω󱵦r��
		List<String> resList = new ArrayList<>();

		// -- �����a,�C�a���u�|���@��,�ҥH���@����,�����[�J��r��
		for (var storeItem : storeList) {
			resList.add("-------------------------------------------�H�U�O�ŦX�n�D�����a�H�ε��");
			// ���@����N�[�i��
			resList.add(
					"���a : " + storeItem.getStoreId() + " ���� : " + storeItem.getCity() + " ���� : " + storeItem.getPoint());
			// --�o�̬O�ǥѩ��W���쪺��Ʈw,�åB���ӵ����Ƨ�
			List<Food> foodList = foodDao.findByStoreIdOrderByFoodpointDesc(storeItem.getStoreId());
			for (var foodItem : foodList) {
				if (foodItem.getFoodpoint() >= foodPoint) {
					resList.add("���a���� : " + foodItem.getFoodid() + " �\�I���� : " + foodItem.getFoodprice() + " ��� : "
							+ foodItem.getFoodpoint());
				}
			}
		}
		return resList;
	}

}
