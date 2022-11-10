package com.example.delicious_map.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.FoodId;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.repository.FoodDao;
import com.example.delicious_map.repository.StoreDao;
import com.example.delicious_map.service.face.StoreFood;

@Service
public class StoreFoodImpl implements StoreFood {
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodDao foodDao;

	// =========1.�s�W�M�ק�Store<�ק�ɷ|���K�����,���|�Q�л\> API 1
	@Override
	public Store AddAndUpdateStore(String store, String city) {
		Store stor = new Store();
		if (!StringUtils.hasText(store) || !StringUtils.hasText(city)) {
			return null;
		}
		Optional<Store> storeop = storeDao.findById(store);
		if (!storeop.isPresent()) { // �s�W
			stor.setStoreId(store);
			stor.setCity(city);
		} else { // �ק�
			stor = storeop.get();
			stor.setCity(city);
		}
		storeDao.save(stor);
		return stor;
	}

	// =========2.�s�W�M�ק�food�åB�s��store������=========================== API 2
	@Override
	public Food AddAndUpdateFood(String store, String food, int price, double point) {
		Food foodvehicle = new Food();
		String pointstr = String.valueOf(point);// �Ndouble�ন�r��
		// --------------

		// --------------
		String pointSubs = pointstr.substring(0, 3);
		double saveFoodPoint = Double.valueOf(pointSubs);
		if (!StringUtils.hasText(store) || !StringUtils.hasText(food) || price <= 0 || point > 5 || point < 1) {
			return null;
		}
		FoodId upfood = new FoodId(store, food);// ���DKEY
		Optional<Food> upfoodop = foodDao.findById(upfood);// �T�{���S�������DKEY �����ܥN��O�ק�
		Optional<Store> Storeop = storeDao.findById(store);
		if (upfoodop.isPresent()) {// �ק諸�P�_��,���i�h�N��n�ק�
			foodvehicle = upfoodop.get();
//			foodvehicle.setStoreid(store);
//			foodvehicle.setFoodid(food);
			foodvehicle.setFoodprice(price);
			foodvehicle.setFoodpoint(Math.floor(point * 10.0) / 10.0); // �૬�L�᪺double
			foodDao.save(foodvehicle);
		} else if (Storeop.isPresent()) { // ���i�h�N��n�s�W�\�I,�B����s�W���b���a�����\�I
			foodvehicle.setFoodid(food);
			foodvehicle.setStoreid(store);
			foodvehicle.setFoodprice(price);
			foodvehicle.setFoodpoint(Math.floor(point * 10.0) / 10.0);// �૬�L�᪺double
			foodDao.save(foodvehicle);
		} else {
			return null;
		}
		// -------vvv-----------------------------------------�ǥ��\�I�������ƺ�X�����s�쩱�a���
		double somefoodpoint = 0;// �s�i���a������쪺�e��
		Store storevehicle = Storeop.get();// ����
		List<Food> foodlist = foodDao.findByStoreId(store);// ���X�Ҧ��۹况�W�����
		for (var foodfor : foodlist) {// �s��
			somefoodpoint += foodfor.getFoodpoint();// ������������[�_��

		}
		somefoodpoint = somefoodpoint / foodlist.size();
		storevehicle.setPoint(Math.floor(somefoodpoint * 10.0) / 10.0);
		storeDao.save(storevehicle);
		return foodvehicle;

	}

	// =========3.�ǥѫ����j�M�쩱�a����T,�åB�����================================ API 3
	@Override
	public List<String> SearchByCity(String city, int search) {
		List<String> list = new ArrayList<>(); // ��l���
		List<Store> citylist = storeDao.findByCity(city); // �ǥ�"����"�h��store"�h��"���
		if (citylist.size() == 0 || !StringUtils.hasText(city)) {
			return null;
		}
		for (var cityfor : citylist) {
			// �ǥѼ���store����Ʈw�h���food�xstoreid(�o�̤]�|���h��)
			List<Food> foodlist = foodDao.findByStoreId(cityfor.getStoreId());
			for (var foodfor : foodlist) {
				list.add("���a : " + cityfor.getStoreId() + " ���a���� : " + cityfor.getPoint() + " �\�I : "
						+ foodfor.getFoodid() + " �\�I���� : " + foodfor.getFoodprice() + " �\�I���� : "
						+ foodfor.getFoodpoint());
			}

		}
		if (search <= 0) {
			return list;
		}
		return list.subList(0, search);
	}
	// =========4.�ǥѵ����j�M�쩱�a����T,�åB�Ƨ�============================ API 4

	@Override
	public List<String> SearchStorePoint(double point) {
		List<String> reslist = new ArrayList<>();
		List<Store> storelist = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point); // ��������H�W���h����ƨåB�Ƨ�
		for (var storefor : storelist) {
			List<Food> foodlist = foodDao.findByStoreId(storefor.getStoreId());
			for (var foodfor : foodlist) {
				reslist.add(" ���a : " + storefor.getStoreId() + " ���� : " + storefor.getCity() + " ���a���� : "
						+ storefor.getPoint() + " �����\�I " + foodfor.getFoodid() + " ���� : " + foodfor.getFoodprice()
						+ " �\�I���� : " + foodfor.getFoodpoint());
			}
		}
		return reslist;
	}
	// =========5.�ǥѩ��a������X���aand�ǥѵ�������X���======================== API 5

	@Override
	public List<String> SearchStorePointAndFoodPoint(double storepoint, double foodpoint) {
		List<Store> storelist = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storepoint);
//		List<Food> foodlist = foodDao.findByFoodpointGreaterThanEqualOrderByFoodpointDesc(foodpoint); //�|���L�h�x���
		List<String> storename = new ArrayList<>();
		for (var storefor : storelist) {// �����a,�C�a���u�|���@��
			storename.add("-------------------------------------------�H�U�O�ŦX�n�D�����a�H�ε��");
			storename.add(// ���@����[�i��
					"���a : " + storefor.getStoreId() + " ���� : " + storefor.getCity() + " ���� : " + storefor.getPoint());
			List<Food> foodlist = foodDao.findByStoreIdOrderByFoodpointDesc(storefor.getStoreId()); //����ƪ��P�ɤ�况�a
			for (var foodfor : foodlist) {// ��粒�C�ӵ��O�_�ŦX�P�_
//*				if (storefor.getStoreId().equals(foodfor.getStoreid())) {
				if (foodfor.getFoodpoint() >= foodpoint) {
					List<String> foodname = new ArrayList<>();// �o�̥����n�ηs���Ŷ�,���M�|��i���ŦX���W�����
					foodname.add("���a���� : " + foodfor.getFoodid() + " �\�I���� : " + foodfor.getFoodprice() + " ��� : "
							+ foodfor.getFoodpoint());
					storename.addAll(foodname); // �C��粒�@��,��i�h�Ĥ@�ө��W��list
//*				}
				}
			}
		}

		return storename;
	}

}
