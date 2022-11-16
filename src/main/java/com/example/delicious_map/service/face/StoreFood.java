package com.example.delicious_map.service.face;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.vo.StoreFoodRes;
import com.example.delicious_map.vo.StoreRes;

public interface StoreFood {

	// �w��<���a>�s�W.�ק�
	public StoreRes addAndUpdateStore(String id, String city);


	// <�\�I>�s�W.�ק�åB�s�i <���a> ������
	public StoreRes addAndUpdateFood(String store, String food, Integer price, double point);


	// �ǥ�<����>��X<���a>�H�ά۹�����<�\�I>... <�åB�i�H����j�M����>
	public List<String> searchByCityAndLimit(String city, int limitNumber);


	// �ǥѵ�����X���a�H�ά۹�������T... �j�M���� //�åB�n�Ƨ�
	public List<String> searchStorePoint(double point);


	// �ǥ�<���a����>��X<���a>�ǥ�<������>��X<���> //�åB��ӳ��n�Ƨ�
	public List<String> searchStorePointAndFoodPoint(double storepoint, double foodpoint);

}
