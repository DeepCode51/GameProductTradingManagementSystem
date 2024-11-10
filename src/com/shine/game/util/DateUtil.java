package com.shine.game.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	//按格式输出当前时间方法
	public static String show() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	}
	public static Date getDate(String str) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Timestamp getTimestamp() {
		return Timestamp.valueOf(show());
	}

}
