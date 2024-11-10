package com.shine.game.dao;

import java.util.List;

import com.shine.game.bean.PageBean;
import com.shine.game.bean.User;

/**
* @version
*/
public interface UserDao {

	//获取总记录数
	long readCount();
	//获取用户列表（分页显示）
	List<User> userList(PageBean pageBean);
	//查找用户名是否存在
	boolean findUser(String userName);
	//查找用户名是否存在
	boolean findUserAndId(String userName,Integer id);
	//根据id获取一个用户的信息
	User findUser(Integer id);
	//增加用户
	boolean userAdd(User user);
	//更新用户
	boolean userUpdate(User user);
	//更新用户的信用
	boolean userUpdateGrade(User user);
	//根据id删除一个用户
	boolean delUser(int id);
	//根据一组id字符串批量删除用户
	boolean batDelUser(String ids);
	//登陆
	User userLogin(User user);
}
