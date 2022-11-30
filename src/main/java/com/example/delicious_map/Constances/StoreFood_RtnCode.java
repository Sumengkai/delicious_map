package com.example.delicious_map.Constances;

public enum StoreFood_RtnCode {
	ADD_SUCCESSFUL("200", "Add SUCCESSFUL"),//api 1 and api 2
	UPDATE_SUCCESSFUL("200","Update SUCCESSFUL"),//api 1 and api 2
	CANT_FIND_STORE("400","Can't find store or city / �䤣�쩱�a�Ϋ���"), // api 1
	CANT_FIND_STOREORFOOD("400","Can't find store or Can't find food / �䤣�쩱�a���\�I"), // api 2
	CANT_FIND_PRICE("400","price Can't null / �������o����"), // api 2
	CANT_FIND_POINT("400","Regulation point 1-5 / �����Q�W�d�b1-5��"), // api 2 
	CANT_FIND_REGULATION_STORE("403","Can't find store / ����s�W���b���a�����\�I"), // api 2 //����s�W���b���a�����\�I
	CANT_FIND_CITYLIST("404"," There are no store in this city / �o�y�����S�����a"), // api 3 
	CANT_FIND_PARAMETER_CITY("400","�������o����"), // api 3
	CANT_FIND_STORELIST_OR_FOODLIST("400","Regulation point 1-5 or too demanding / �����Q�W�d�b1-5���άO�S���ŦX���󪺸�T"); //  api 4 and api 5
	private String code;
	private String message;
	//�u���\�p��
	private StoreFood_RtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
