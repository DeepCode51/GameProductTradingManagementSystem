package com.shine.game.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shine.game.bean.OrderItem;
import com.shine.game.dao.OrderItemDao;
import com.shine.game.util.DbUtil;

/**
* @version
*/
public class OrderItemDaoImpl implements OrderItemDao {

	@Override
	public boolean orderAdd(OrderItem orderItem) {
		String sql="insert into orderItem(bookId,orderId,quantity) values(?,?,?)";

		int i= DbUtil.excuteUpdate(sql,orderItem.getBookId(),orderItem.getOrderId(),orderItem.getQuantity());

		return i>0?true:false;
	}

	@Override
	public List<OrderItem> findItemByOrderId(int orderId) {
		List<OrderItem> lo=new ArrayList<>();
		String sql="select * from orderItem where orderId=?";
		List<Map<String, Object>> query = DbUtil.executeQuery(sql, orderId);
		if(query.size()>0) {
			for(Map<String,Object> map:query) {
				OrderItem oItem=new OrderItem(map);
				lo.add(oItem);
			}

		}
		return lo;
	}

	/**
	 *
	 * @param itemId 订单明细id
	 * @param state 是否评价(0:未评价,1:已评价)
	 * @return
	 */
	@Override
	public boolean updateIsComment(int itemId,int state) {
		String sql="update orderitem set IsComment=? where itemId =?";
		int i=DbUtil.excuteUpdate(sql,state,itemId);
		return i>0?true:false;
	}

}
