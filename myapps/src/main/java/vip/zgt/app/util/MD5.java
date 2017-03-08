package vip.zgt.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private static MessageDigest md5 = null;
	
	static {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static String String2MD5 (String str) {
		StringBuffer sb = new StringBuffer(32);
		try {
			byte[] array = md5.digest(str.getBytes("UTF-8"));
			for (byte b:array) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x100).toUpperCase().substring(1, 3));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String pwd = String2MD5("123456");
		System.out.println(pwd);
	}
}
