package com.shine.game.util;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
* @version
*/
public class Test {

	public static void main(String[] args) throws MalformedURLException {
//		System.out.println(DbUtil.executeQuery("select * from view2"));
////		String uuid=UUID.randomUUID().toString().replaceAll("-", "");
//
////		System.out.println(RanUtil.getUUID().length());
//
//		File f=new File("images/book/"+RanUtil.getUUID()+".jpg");
//
//
//		System.out.println(f.toURL());

//		String sql = "insert into book(bookName,catalogId,price,imgId,desc) values(?,?,?,?,?)";
//		System.out.println(DbUtil.excuteUpdate(sql, "sdafs",2,98.9,12,"dsafsda"));
//
		String imgIds="";
		String ids="1,6";
		String sql="select imgId from book where bookId in("+ids+")";

		List<Map<String, Object>> list = DbUtil.executeQuery(sql);
		if(list.size()>0) {
			for(int i=0;i<list.size();i++) {
				if(i!=list.size()-1) {
					imgIds+=list.get(i).get("imgId")+",";
				}else {
					imgIds+=list.get(i).get("imgId");
				}

			}
		}
		System.out.println(imgIds);
	}

}
