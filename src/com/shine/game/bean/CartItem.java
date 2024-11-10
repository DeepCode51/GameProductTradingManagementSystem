package com.shine.game.bean;

import com.shine.game.util.MathUtils;

/**
* @version
*/
public class CartItem {
	private Book book;
	private int quantity;//数量
	private double subtotal;//小计

	public CartItem() {}



	public CartItem(Book book, int quantity) {
		super();
		this.setBook(book);
		this.setQuantity(quantity);
	}

	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		this.subtotal = MathUtils.getTwoDouble(quantity*book.getPrice());
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}



	@Override
	public String toString() {
		return "CartItem [book=" + book + ", quantity=" + quantity + ", subtotal=" + subtotal + "]";
	}



}
