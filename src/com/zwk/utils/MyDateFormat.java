package com.zwk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyDateFormat {
	/**
	 * 获取格式化时间
	 * @param dateString
	 * @param formatString
	 * @return
	 */
	public static String getDate(String dateString, String formatString) {
		try {
			// 根据传入的formatString解析时间
			String formattedDateString =new SimpleDateFormat(formatString).format(
					// 整个字符串的解析
					new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateString));
			return formattedDateString;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return " ";
	}
}
