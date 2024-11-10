package com.shine.game.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
/**
 *
 * @author thh
 * 数据库工具类
 */
public class DbUtil {
	public static String BASE_NAME="dbinfo";
	private static String DB_DRIVER=PropertiesUtil.getValue("DB_DRIVER");
	private static String DB_URL=PropertiesUtil.getValue("DB_URL");
	private static String DB_USERNAME=PropertiesUtil.getValue("DB_USERNAME");
	private static String DB_PASSWORD=PropertiesUtil.getValue("DB_PASSWORD");

	static{
		try {
			Class.forName(DB_DRIVER);
		} catch (Exception e) {
			System.out.println("*****驱动加载失败*****");
		}
	}

	/**
	 *
	 * @param sql	sql语句（增删改）
	 * @param obj	Object...obj为可变长度参数，可有可无
	 * @return		返回受影响的行数 （int）
	 */
	public static int excuteUpdate(String sql,Object...obj) {
		int row=0;
		Connection con=DbUtil.getConnection();
		PreparedStatement pst=null;
		try {
			pst=con.prepareStatement(sql);
			if(obj!=null&&obj.length>0) {
				for(int i=0;i<obj.length;i++) {
					pst.setObject(i+1, obj[i]);
				}
			}
			row=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose(con, pst);

		}
		return row;
	}

	/**
	 *
	 * @param sql 	sql查询语句
	 * @param obj	Object...obj为可变长度参数，可有可无
	 * @return		返回list集合（list<Map<String,Object>>)
	 */
	//查询用的方法
	public static List<Map<String,Object>> executeQuery(String sql,Object...obj){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Connection con=DbUtil.getConnection();
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst=con.prepareStatement(sql);
			System.out.println(sql);
			if(obj!=null&&obj.length>0) {
				for(int i=0;i<obj.length;i++) {
					pst.setObject(i+1, obj[i]);
				}
			}
			rs=pst.executeQuery();
			//把结果集转换为内存中一张虚拟表
			ResultSetMetaData rsmd=rs.getMetaData();
			//获取表中的列数
			int colCount=rsmd.getColumnCount();
			if(rs!=null) {
				while(rs.next()) {
					Map<String,Object> mso=new HashMap<String,Object>();
					for(int i=1;i<=colCount;i++) {
						//rsmd.getColumnName(i);获取每列的列名
						mso.put(rsmd.getColumnName(i), rs.getObject(i));
					}
					list.add(mso);
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
		}finally {
			dbClose(con, pst, rs);
		}
		return	list;
	}

	/**
	 *
	 * @return 返回一个Connection连接对象
	 */
	private static  Connection getConnection() {
		Connection conn=null;
		try {
			conn=(Connection) DriverManager.getConnection(DB_URL,DB_USERNAME, DB_PASSWORD);

		} catch (SQLException e) {
			System.out.println(DateUtil.show()+">>数据库连接失败!"+e.getMessage());
		}
		return conn;

	}
	/**
	 *
	 * @param conn 要关闭Connection对象
	 * @param pstm 要关闭PreparedStatement对象
	 */
	private static void dbClose(Connection conn,PreparedStatement pstm) {
		try {
			if(conn!=null) {
				conn.close();
			}
			if(pstm!=null) {
				pstm.close();
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	/**
	 *
	 * @param conn 要关闭Connection对象
	 * @param pstm 要关闭PreparedStatement对象
	 * @param rs   要关闭ResultSet对象
	 */
	private static void dbClose(Connection conn,PreparedStatement pstm,ResultSet rs) {
		try {
			if(conn!=null) {
				conn.close();
			}
			if(pstm!=null) {
				pstm.close();
			}
			if(rs!=null) {
				rs.close();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
