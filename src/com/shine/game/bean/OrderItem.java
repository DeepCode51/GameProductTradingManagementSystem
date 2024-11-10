package com.shine.game.bean;

import java.util.Map;

/**
 * 订单项
 *
 * @author
 * @create 2020-09-23 1:53
 */
public class OrderItem {

    private int itemId;          //订单项编号
    private int bookId;         //饰品编号
    private int orderId;        //订单编号
    private int quantity;       //数量
    private int isComment;       //是否评价

    private Book book;

    public OrderItem() {
    }


    public OrderItem(Map<String, Object> map) {
		this.setOrderId((int) map.get("orderId"));
		this.setBookId((int) map.get("bookId"));
		this.setItemId((int) map.get("itemId"));
		this.setQuantity((int) map.get("quantity"));
		this.setIsComment((int) map.get("isComment"));
	}


	public int getItemId() {
		return itemId;
	}


	public void setItemId(int itemId) {
		this.itemId = itemId;
	}


	public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


	public Book getBook() {
		return book;
	}


	public void setBook(Book book) {
		this.book = book;
	}

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }
}
