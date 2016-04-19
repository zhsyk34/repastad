package com.baiyi.order.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesReadAndWrite {
	// 根据key读取value
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			// System.out.println(key + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取properties的全部信息
	public static void readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(key + Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 写入properties信息
	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			prop.setProperty(parameterName, parameterValue);

			OutputStream fos = new FileOutputStream(filePath);
			prop.store(fos, "Update '" + parameterName + "' value");

			fis.close();
			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
		}
	}

	/*
	 * public static void main(String[] args) { readValue("info.properties",
	 * "url"); writeProperties("info.properties", "age", "22");
	 * readProperties("info.properties"); System.out.println("OK"); }
	 */
}