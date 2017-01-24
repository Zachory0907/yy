package com.zgt.digital.util;

public class UUID {
	
	public static final String getUUID() {
		return java.util.UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
	
	public static final String getUUID(int num) {
		return java.util.UUID.randomUUID().toString().replace("-", "").toLowerCase().substring(0, num);
	}
	
}
