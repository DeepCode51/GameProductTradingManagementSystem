package com.shine.game.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RanUtil {

	public static String getOrderNum(){
		String date=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//随机生成uuid唯一识别码，获取hashcode码
		int code=UUID.randomUUID().hashCode();
		if(code<0){
			code=-code;
		}
		String strCode=String.format("%08d",code);
		return date+strCode;

	}

	public static String getUUID(){

		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
