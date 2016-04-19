package com.baiyi.order.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidateUtil {

	public static boolean isNotEmpty(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return true;
	}

	public static boolean isStrictNotEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		return true;
	}

	public static boolean isNotEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			return false;
		}
		return true;
	}

	public static Date strToDate(String str) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!isNotEmpty(str)) {
			return null;
		}
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String dateToStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	// public static boolean date(Date d1, Date d2) {
	// return false;
	// }

}
