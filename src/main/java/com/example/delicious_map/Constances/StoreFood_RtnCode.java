package com.example.delicious_map.Constances;

public enum StoreFood_RtnCode {
	ADDSUCCESSFUL("200", "Add SUCCESSFUL"),//api 1 and api 2
	UPDATESUCCESSFUL("200","Update SUCCESSFUL"),//api 1 and api 2
	CANT_FIND_STORE("400","Can't find store or city "), // api 1
	CANT_FIND_KEY("400","Can't find store or Can't find food"), // api 2
	CANT_FIND_PRICE("400","price Can't null or < 0"), // api 2
	CANT_FIND_POINT("400","Regulation point 1-5 "), // api 2 
	CANT_FIND_REGULATION("403","Can't find store"), // api 2 //不能新增不在店家內的餐點
	CANT_FIND_CITYLIST("404"," There are no store in this city"), // api 3 
	CANT_FIND_POINTLIST("400","Regulation point 1-5 or too demanding"); //  api 4 and api 5
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
