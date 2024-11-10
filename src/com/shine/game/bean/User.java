package com.shine.game.bean;

import java.util.Map;

public class User {

    private int userId;         //用户编号
    private String userName;    //用户名
    private String userPassWord;    //用户密码
	private  String payPwd;//支付密码
    private String name;		//用户姓名
    private String sex;         //用户性别
    private int age;            //用户年龄
    private String tell;        //用户电话
    private String address;     //用户地址
    private String enabled;		//用户状态y启用n禁用
	private String grade ;     //卖家信用等级
    public User() {}

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public User(String userName, String userPassWord) {
		super();
		this.userName = userName;
		this.userPassWord = userPassWord;
	}



	public User(String userName, String userPassWord, String name, String sex, int age, String tell, String address) {
		super();
		this.userName = userName;
		this.userPassWord = userPassWord;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.tell = tell;
		this.address = address;

	}


	public User(int userId, String userPassWord, String name, String sex, int age, String tell, String address,
			String enabled) {
		super();
		this.userId = userId;
		this.userPassWord = userPassWord;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.tell = tell;
		this.address = address;
		this.enabled = enabled;
	}



	public User(Map<String,Object> map) {
    	userId=(int) map.get("userId");
    	userName=(String) map.get("userName");
    	userPassWord=(String) map.get("userPassWord");
    	name=(String) map.get("name");
    	sex=(String) map.get("sex");
    	age=(int) map.get("age");
    	payPwd=(String)map.get("paypwd");
    	tell=(String) map.get("tell");
    	address=(String) map.get("address");
    	enabled=(String) map.get("enabled");
		grade=(String)map.get("grade");
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassWord() {
		return userPassWord;
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}


	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", userPassWord='" + userPassWord + '\'' +
				", payPwd='" + payPwd + '\'' +
				", name='" + name + '\'' +
				", sex='" + sex + '\'' +
				", age=" + age +
				", tell='" + tell + '\'' +
				", address='" + address + '\'' +
				", enabled='" + enabled + '\'' +
				", grade='" + grade + '\'' +
				'}';
	}



}
