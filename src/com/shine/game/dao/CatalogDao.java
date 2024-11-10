package com.shine.game.dao;

import java.util.List;

import com.shine.game.bean.Catalog;
import com.shine.game.bean.PageBean;

/**
 * 饰品分类dao层
 * @author
 *
 */
public interface CatalogDao {
	//获取饰品分类信息
	List<Catalog> getCatalog();
	//获取饰品分类信息（分页）
	List<Catalog> catalogList(PageBean pb);
	//统计总数
	long catalogReadCount();
	//分类删除
	boolean catalogDel(int catalogId);
	//分类批量删除
	boolean catalogBatDelById(String ids);
	//查找分类名称
	boolean findCatalogByCatalogName(String catalogName);
	//按照分类id查询
	Catalog findCatalogByCatalogId(int catalogId);
	//增加分类
	boolean catalogAdd(String catalogName);

	boolean catalogUpdate(Catalog catalog);
}
