package com.example.delicious_map.Constances;

public enum StoreFood_RtnCode {
	ADD_SUCCESSFUL("200", "Add SUCCESSFUL"),//api 1 and api 2
	UPDATE_SUCCESSFUL("200","Update SUCCESSFUL"),//api 1 and api 2
	CANT_FIND_STORE("400","Can't find store or city / 找不到店家或城市"), // api 1
	CANT_FIND_STOREORFOOD("400","Can't find store or Can't find food / 找不到店家或餐點"), // api 2
	CANT_FIND_PRICE("400","price Can't null / 價錢不得為空"), // api 2
	CANT_FIND_POINT("400","Regulation point 1-5 / 評價被規範在1-5分"), // api 2 
	CANT_FIND_REGULATION_STORE("403","Can't find store / 不能新增不在店家內的餐點"), // api 2 //不能新增不在店家內的餐點
	CANT_FIND_CITYLIST("404"," There are no store in this city / 這座城市沒有店家"), // api 3 
	CANT_FIND_PARAMETER_CITY("400","城市不得為空"), // api 3
	CANT_FIND_STORELIST_OR_FOODLIST("400","Regulation point 1-5 or too demanding / 評價被規範在1-5分或是沒有符合條件的資訊"); //  api 4 and api 5
	private String code;
	private String message;
	//只允許私有
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
