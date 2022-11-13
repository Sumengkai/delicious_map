package com.example.delicious_map.service.face;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.example.delicious_map.entity.Food;
import com.example.delicious_map.entity.Store;
import com.example.delicious_map.vo.StoreRes;

public interface StoreFood {
	//------------------------------------------------------1
	// �w��store�s�W.�ק�
	public StoreRes AddAndUpdateStore(String id, String city);

//------------------------------------------------------2

	// food�s�W�ק�åB�s�istore������
	public Food AddAndUpdateFood(String store, String food, int price, double point);

//------------------------------------------------------3
	// �ǥѫ�����X���a�H�ά۹������\�I... �j�M����
	public List<String> SearchByCity(String city, int num);

// ------------------------------------------------------4
	// �ǥѵ�����X���a�H�ά۹�������T... �j�M���� //�åB�n�Ƨ�
	public List<String> SearchStorePoint(double point);

//-------------------------------------------------------5
	//�ǥѩ��a������X���aand�ǥѵ�������X��� //�åB��ӳ��n�Ƨ�
	public List<String> SearchStorePointAndFoodPoint(double storepoint, double foodpoint);

}
