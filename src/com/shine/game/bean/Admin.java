package com.shine.game.bean;

import java.util.Date;
import java.util.Map;
/**
 * 管理员用户类
 * @author
 *
 */
public class Admin {
	private Integer id;				//用户编号
	private String userName;			//用户名
	private String passWord;			//用户密码
	private String name;				//用户姓名
	private Date lastLoginTime;		//最后登录时间

	public Admin() {}

	public Admin(String userName,String passWord) {
		this.userName=userName;
		this.passWord=passWord;
	}

	public Admin(Integer id, String passWord, String name) {
		super();
		this.id = id;
		this.passWord = passWord;
		this.name = name;
	}


	public Admin(String userName, String passWord, String name) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.name = name;
	}

	public Admin(Map<String,Object> map) {
		this.id = (Integer) map.get("id");
		this.userName=(String) map.get("userName");
		this.passWord = (String) map.get("passWord");
		this.name = (String) map.get("name");
		this.lastLoginTime=(Date) map.get("lastLoginTime");
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public String toString() {
		return "Admin{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", passWord='" + passWord + '\'' +
				", name='" + name + '\'' +
				", lastLoginTime=" + lastLoginTime +
				'}';
	}
}
