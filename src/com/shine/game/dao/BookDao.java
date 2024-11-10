package com.shine.game.dao;

import java.util.List;

import com.shine.game.bean.Book;
import com.shine.game.bean.PageBean;

public interface BookDao {
	// 获取饰品总数
	long bookReadCount();



	//获取饰品搜索总数
	long bookReadCountLikeName(String name);

	// 获取饰品分页列表(视图)
	List<Book> bookList(PageBean pageBean);

	// 按分类获取饰品数量
	long bookReadCount(int catalogId);

	// 按分类获取饰品分页列表(视图)
	List<Book> bookList(PageBean pageBean, int catalogId);
	List<Book> bookListLikeName(PageBean pageBean,String name) ;
	// 增加饰品
	boolean bookAdd(Book book);

	// 根据饰品id查找饰品信息(视图)
	Book findBookById(int bookId);

	// 根据饰品名称查找饰品是否存在
	boolean findBookByBookName(String bookName);

	// 更新饰品信息
	boolean bookUpdate(Book book);

	// 根据id删除饰品
	boolean bookDelById(int bookId);

	// 根据id串查询图片id串
	String findimgIdByIds(String ids);

	// 批量删除饰品
	boolean bookBatDelById(String ids);

	// 随机获取指定数量书
	List<Book> bookList(int num);

	// 获取指定数量新添加的饰品
	List<Book> newBooks(int num);

	long bookReadCountByUserId(int userId);

	// 获取饰品分页列表(视图)
	List<Book>  bookListByUserId(PageBean pb ,int userId);
}
