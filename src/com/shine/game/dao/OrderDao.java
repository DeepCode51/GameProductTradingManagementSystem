package com.shine.game.dao;

import java.util.List;

import com.shine.game.bean.Order;
import com.shine.game.bean.PageBean;

/**
* @version
*/
public interface OrderDao {

	//增加一个订单记录
	boolean orderAdd(Order order);
	//查找订单编号通过订单号
	int findOrderIdByOrderNum(String orderNum);
	//统计总订单数
	long orderReadCount();
	//统计总订单数(by userId)
	long orderReadCount(int userId);
	//统计总订单数(by orderStatus)
	long orderReadCountByStatus(int status);
	//统计总订单数(by orderStatus)
	long orderReadCountByStatusUserId(int status,int userid);
	//获得订单列表（分页）,条件用户id
	List<Order> orderList(PageBean pageBean);
	//获得订单列表（分页）,条件用户id
	List<Order> orderList(PageBean pageBean,int userId);
	//获得订单列表（分页）,条件orderStatus
	List<Order> orderListByStatus(PageBean pb, int status);

	//获得订单列表（分页）,条件orderStatus
	List<Order> orderListByStatusUserId(PageBean pb, int status,int userid);
	//查找订单编号通过订单号
	Order findOrderByOrderId(int orderId);

	//更改订单状态
	boolean orderStatus(int orderId,int status);



}
