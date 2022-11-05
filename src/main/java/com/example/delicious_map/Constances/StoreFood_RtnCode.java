package com.example.delicious_map.Constances;

public enum StoreFood_RtnCode {
	SUCCESSFUL("200", "成功"),
	CANT_FIND_Key("400","Can't find store or Can't find food"), // api 2
	CANT_FIND_price("400","price Can't>0"), // api 2
	CANT_FIND_point("400","Regulation point 1-5 Fraction"), // api 2
	CANT_FIND_Regulation("400","Regulation point EX: 2.5 or Can't find store沒有該店家的情況不允許新增菜單"), // api 2
	CANT_FIND_LIST("400"," There are no store in this city"), // api 3 
	CANT_FIND_POINTLIST("400","Point Can't > 5 or too demanding"), //  api 4 and 5
	CANT_FIND_STORE("400","Can't find store or city"); // api 1
	private String code;
	private String message;
	//只允許私有
	private StoreFood_RtnCode(String a, String b) {
		this.code = a;
		this.message = b;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
