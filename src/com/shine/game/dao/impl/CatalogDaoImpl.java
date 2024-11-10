package com.shine.game.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shine.game.bean.Book;
import com.shine.game.bean.Catalog;
import com.shine.game.bean.PageBean;
import com.shine.game.dao.CatalogDao;
import com.shine.game.util.DbUtil;

public class CatalogDaoImpl implements CatalogDao {

    /**
     * 获取饰品分类信息
     */
    @Override
    public List<Catalog> catalogList(PageBean pb) {
        List<Catalog> list = new ArrayList<Catalog>();
        String sql = "select * from catalog limit ?,?";
        // 查询的分页结果集
        List<Map<String, Object>> lm = DbUtil.executeQuery(sql, (pb.getCurPage() - 1) * pb.getMaxSize(),
                pb.getMaxSize());
        if (lm.size() > 0) {
            for (Map<String, Object> map : lm) {
                Catalog catalog = new Catalog(map);
                list.add(catalog);
            }
        }
        return list;
    }

    @Override
    public long catalogReadCount() {
        long count = 0;
        String sql = "select count(*) as count from catalog";
        List<Map<String, Object>> lm = DbUtil.executeQuery(sql);
        if (lm.size() > 0) {
            count = (long) lm.get(0).get("count");
        }
        return count;
    }

    @Override
    public List<Catalog> getCatalog() {
        List<Catalog> list = new ArrayList<Catalog>();
        String sql = "select * from catalog";

        List<Map<String, Object>> lmso = DbUtil.executeQuery(sql);
        if (lmso.size() > 0) {
            for (Map<String, Object> map : lmso) {
                Catalog catalog = new Catalog(map);
                list.add(catalog);
            }
        }
        return list;
    }

    @Override
    public boolean catalogDel(int catalogId) {
        String sql = "delete from catalog where catalogId=?";
        int i = DbUtil.excuteUpdate(sql, catalogId);
        return i > 0 ? true : false;
    }

    @Override
    public boolean catalogBatDelById(String ids) {
        String sql = "delete from catalog where catalogId in(" + ids + ")";
        int i = DbUtil.excuteUpdate(sql);
        return i > 0 ? true : false;
    }

    @Override
    public boolean findCatalogByCatalogName(String catalogName) {
        String sql = "select * from catalog where catalogName=?";
        List<Map<String, Object>> list = DbUtil.executeQuery(sql, catalogName);
        return list.size() > 0 ? true : false;
    }

    @Override
    public Catalog findCatalogByCatalogId(int catalogId) {
        String sql = "select * from catalog where catalogId=?";
        Catalog book = null;
        List<Map<String, Object>> list = DbUtil.executeQuery(sql, catalogId);
        if (list.size() > 0) {
            book = new Catalog(list.get(0));
        }
        return book;
    }

    @Override
    public boolean catalogAdd(String catalogName) {
        String sql = "insert into catalog(catalogName) values(?)";
        int i = DbUtil.excuteUpdate(sql, catalogName);
        return i > 0 ? true : false;
    }

    /**
     * 更新分类信息
     */
    @Override
    public boolean catalogUpdate(Catalog catalog) {
        String sql = "update catalog set catalogName=? where catalogId=?";
        int i = DbUtil.excuteUpdate(sql, catalog.getCatalogName(), catalog.getCatalogId());
        return i > 0 ? true : false;
    }


}
