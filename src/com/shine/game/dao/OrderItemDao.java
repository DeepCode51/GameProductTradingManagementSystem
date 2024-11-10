package com.shine.game.dao;

import java.util.List;

import com.shine.game.bean.OrderItem;

/**
* @version
*/
public interface OrderItemDao {
	//增加一个订单项记录
	boolean orderAdd(OrderItem orderItem);
	//通过订单编号查找订单项
	List<OrderItem> findItemByOrderId(int orderId);

	/**
	 *
	 * @param itemId 订单明细id
	 * @param state 是否评价(0:未评价,1:已评价)
	 * @return
	 */
	 boolean updateIsComment(int itemId,int state) ;
}
