package com.shine.game.util;

import java.util.ResourceBundle;

/**
 * 读取dbInfo.properties的参数类
 * @author
 *
 */
public class PropertiesUtil {

	private static ResourceBundle bundle;



	public static String getValue(String key){
		try {
			bundle=ResourceBundle.getBundle(DbUtil.BASE_NAME);//这里通过类常量(db文件名)获取资源
		} catch (Exception e) {
			e.getStackTrace();
		}

		return bundle.getString(key);
	}



//	public static void main(String[] args) {
//		String Driver=PropertiesUtil.getValue("DB_DRIVER");
//		System.out.println(Driver);
//	}

//	public static String getValue(String name){
//		Properties p=new Properties();
//		try {
//			//查找dbInfo.properties文件。并返回输入流
//			InputStream is=PropertiesUtil.class.getResourceAsStream("/dbinfo.properties");
//			p.load(is);
//		} catch (IOException e) {
//			System.out.println(DateUtil.show()+">>数据库配置文件加载失败!"+e.getMessage());
//		}
//
//		return p.getProperty(name);
//	}
}
