package com.example.delicious_map.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Food_id;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.repository.FoodDao;
import com.example.delicious_map.repository.StoreDao;
import com.example.delicious_map.service.face.StoreFood_face;

@Service
public class StoreFood_impl implements StoreFood_face {
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private FoodDao foodDao;

	// =========1.�s�W�M�ק�Store<�ק�ɷ|���K�����,���|�Q�л\> API 1
	@Override
	public Store AddAndUpStore(String store, String city) {
		Store stor = new Store();
//		storeDao.findById(store);
		if (!StringUtils.hasText(store) || !StringUtils.hasText(city)) {
			return null;
		}

		stor.setStoreId(store);//�s�W
		stor.setCity(city);//�s�W

		double updatepoint = 0;
		List<Food> foodlist = foodDao.findByStoreId(store);// ���X�Ҧ��۹况�W��foodlist
		for (var foodfor : foodlist) { // �i�Ӯ� �N��O�ק�
			if (foodfor.getStoreid().equals(store)) {
				updatepoint += foodfor.getFoodpoint() / foodlist.size();
				String chickfoodpoint = String.valueOf(updatepoint);// �p�⵲�G�ন�r��,�]���n���p���I�Ĥ@��
				String stringpount = chickfoodpoint.substring(0, 3);// ����ĴX��ƥH�᪺�Ʀr
				double saveStorePoint = Double.parseDouble(stringpount);// �A��^double,�s�i�h��Ʈw
				stor.setPoint(saveStorePoint);
				stor.setStoreId(store);
				stor.setCity(city);
			}
		}
		storeDao.save(stor);
		return stor;
	}

	// =========2.�s�W�M�ק�food�åB�s��store������=========================== API 2
	@Override
	public Food AddAndUpFood(String store, String food, int price, double point) {
		Food foodvehicle = new Food();
		String chick = "\\d.\\d"; // �ˬd�O�_�ŦX�W��
		String pointstr = String.valueOf(point);// �Ndouble�ন�r��
		boolean chickpoint = pointstr.matches(chick);// �åB�T�{�åB�^�ǬO�_
		if (!StringUtils.hasText(store) || !StringUtils.hasText(food) || price < 0 || !chickpoint || point > 5
				|| point < 1) {
			return null;
		}
		Food_id upfood = new Food_id(store, food);// ���DKEY
		Optional<Food> upfoodop = foodDao.findById(upfood);// �T�{���S�������DKEY �����ܥN��O�ק�
		Optional<Store> Storeop = storeDao.findById(store);
		if (upfoodop.isPresent()) {// �ק諸�P�_��,���i�h�N��n�ק�
//			foodvehicle = upfoodop.get();
			foodvehicle.setStoreid(store);
			foodvehicle.setFoodid(food);
			foodvehicle.setFoodprice(price);
			foodvehicle.setFoodpoint(point);
			foodDao.save(foodvehicle);
		} else if (Storeop.isPresent()) { // ���i�h�N��n�s�W�\�I,����s�W���b���a�����\�I
			foodvehicle.setFoodid(food);
			foodvehicle.setStoreid(store);
			foodvehicle.setFoodprice(price);
			foodvehicle.setFoodpoint(point);
			foodDao.save(foodvehicle);
		} else {
			return null;
		}

		// -------//�i���\�s�Wstore�S�������a�����vvv(���ѳ���)
//		Optional<Store> Storeop = storeDao.findById(store); // �����a����Ʈw,�u�|���@�ө��a//�i���\�s�Wstore�S�������a�����
//		if (!Storeop.isPresent()) {
//			return foodvehicle;
//		}
		// -------vvv-----------------------------------------�ǥ��\�I�������ƺ�X�����s�쩱�a���
		double somefoodpoint = 0;// �s�i���a������쪺�e��
		Store storevehicle = Storeop.get();// ����
		List<Food> foodlist = foodDao.findByStoreId(store);// ���X�Ҧ��۹况�W�����
		for (var foodfor : foodlist) {// �s��
			if (storevehicle.getStoreId().equals(foodfor.getStoreid())) { // �p�G�����,��ܭn�ק�
				somefoodpoint += foodfor.getFoodpoint();// ������������[�_��
			}
//			else { �i���\�s�Wstore�S�������a�����n�[���P�_
//				return foodvehicle; 
//			}
		}
		somefoodpoint = somefoodpoint / foodlist.size();
		String chickfoodpoint = String.valueOf(somefoodpoint);// �p�⵲�G�ন�r��,�]���n���p���I�Ĥ@��
		String x = chickfoodpoint.substring(0, 3);// ����ĴX��ƥH�᪺�Ʀr
		double saveStorePoint = Double.parseDouble(x);// �A��^double,�s�i�h��Ʈw
		storevehicle.setPoint(saveStorePoint);
		storeDao.save(storevehicle);
		return foodvehicle;

	}

	// =========3.�ǥѫ����j�M�쩱�a����T,�åB�����================================ API 3
	@Override
	public List<String> SearchByCity(String city, int search) {
		List<String> list = new ArrayList<>(); // ��l���
		List<Store> citylist = storeDao.findByCity(city); // �ǥ�"����"�h��store"�h��"���
		if (citylist.size() == 0 || !StringUtils.hasText(city) || search < 0) {
			return null;
		}
		for (var cityfor : citylist) {
			// �ǥѼ���store����Ʈw�h���food�xstoreid(�o�̤]�|���h��)
			List<Food> foodlist = foodDao.findByStoreId(cityfor.getStoreId());
			for (var foodfor : foodlist) {
				if (cityfor.getStoreId().equals(foodfor.getStoreid())) {
					list.add("���a : " + cityfor.getStoreId() + " ���a���� : " + cityfor.getPoint() + " �\�I : "
							+ foodfor.getFoodid() + " �\�I���� : " + foodfor.getFoodprice() + " �\�I���� : "
							+ foodfor.getFoodpoint());
				}
			}

		}
		if (search >= list.size() || search == 0) {
			return list;
		}
		List<String> reslist = list.subList(0, search);

		return reslist;
	}
	// =========4.�ǥѵ����j�M�쩱�a����T,�åB�Ƨ�============================ API 4

	@Override
	public List<String> SearchStorePoint(double point) {
		List<String> reslist = new ArrayList<>();
		List<Store> storelist = storeDao.findByPointGreaterThanEqualOrderByPointDesc(point); // ��������H�W���h����ƨåB�Ƨ�
		for (var storefor : storelist) {
			List<Food> foodlist = foodDao.findByStoreId(storefor.getStoreId());
			for (var foodfor : foodlist) {
				if (storefor.getStoreId().equals(foodfor.getStoreid())) {
					reslist.add("���a :" + storefor.getStoreId() + "���� :" + storefor.getCity() + "���a���� :"
							+ storefor.getPoint() + "�����\�I" + foodfor.getFoodid() + "���� :" + foodfor.getFoodprice()
							+ "�\�I���� :" + foodfor.getFoodpoint());
				}
			}
		}
		return reslist;
	}
	// =========5.�ǥѩ��a������X���aand�ǥѵ�������X���======================== API 5

	@Override
	public List<String> SearchStorePointAndFoodPoint(double storepoint, double foodpoint) {
		// TODO Auto-generated method stub
		List<Store> storelist = storeDao.findByPointGreaterThanEqualOrderByPointDesc(storepoint);
		List<Food> foodlist = foodDao.findByFoodpointGreaterThanEqualOrderByFoodpointDesc(foodpoint);
		List<String> storename = new ArrayList<>();
		for (var storefor : storelist) {// �����a,�C�a���u�|���@��
			storename.add("=============�H�U�O�ŦX�n�D�����a�H�ε��");
			storename.add(// ���@����[�i��
					"���a : " + storefor.getStoreId() + " ���� : " + storefor.getCity() + " ���� : " + storefor.getPoint());
			for (var foodfor : foodlist) {// ��粒�C�ӵ��O�_�ŦX�P�_
				if (storefor.getStoreId().equals(foodfor.getStoreid())) {
					List<String> foodname = new ArrayList<>();// �o�̥����n�ηs���Ŷ�,���ŦX���W�����
					foodname.add("���a���� : " + foodfor.getFoodid() + " �\�I���� : " + foodfor.getFoodprice() + " ��� : "
							+ foodfor.getFoodpoint());
					storename.addAll(foodname); // �C��粒�@��,��i�h�Ĥ@�ө��W��list
				}

			}
		}

		return storename;
	}

}
