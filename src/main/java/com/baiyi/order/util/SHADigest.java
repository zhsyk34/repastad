package com.baiyi.order.util;

import java.security.MessageDigest;

public class SHADigest {

	//信息摘要加密
	public static String encode(String content) {
		String str = "";
		try {
			MessageDigest alga = MessageDigest.getInstance("SHA-1");
			alga.update(content.getBytes());
			byte[] digesta = alga.digest();
			str = byte2hex(digesta);
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println("非法摘要算法");
		}
		return str;
	}

	// 二行制转字符串
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}
	
	public static void main(String[] args) {
		System.out.println(encode("root"));
	}
}