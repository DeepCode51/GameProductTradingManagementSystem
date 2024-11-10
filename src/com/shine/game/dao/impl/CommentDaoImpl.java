package com.shine.game.dao.impl;

import com.shine.game.bean.Comment;
import com.shine.game.bean.PageBean;
import com.shine.game.dao.CommentDao;
import com.shine.game.util.DbUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentDaoImpl implements CommentDao {

    @Override
    public long commentReadCount() {
        String sql = "select count(*) as count from comment";
        List<Map<String, Object>> lm = DbUtil.executeQuery(sql);
        return lm.size() > 0 ? (long) lm.get(0).get("count") : 0;
    }

    @Override
    public List<Comment> commentList(PageBean pageBean) {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT * from comment c left join book s on c.gid =s.bookId  left join user u on c.uid=u.userId ORDER BY c.time DESC  limit ?,?";
        // 查询的分页结果集
        List<Map<String, Object>> lm = DbUtil.executeQuery(sql, (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        // 把查询的book结果由List<Map<String, Object>>转换为List<Book>
        if (lm.size() > 0) {
            for (Map<String, Object> map : lm) {
                Comment book = new Comment(map);
                list.add(book);
            }
        }
        return list;
    }

    /**
     *
     * @param gid 商品id
     * @param uid 用户id
     * @return
     */
    @Override
    public boolean findCommentByUid(Integer gid, Integer uid) {
        String sql = "SELECT * from comment where gid=? and uid=?";
        System.out.println("执行sql");
        System.out.println(sql);
        List<Map<String, Object>> i = DbUtil.executeQuery(sql, gid,uid);
        return i.size() > 0 ? true : false;
    }

    @Override
    public Comment findComment(Integer id) {
        String sql = "SELECT * from comment c left join book s on c.gid =s.bookId  left join user u on c.uid=u.userId where 1=1 and id=?";
        Comment book = null;
        List<Map<String, Object>> list = DbUtil.executeQuery(sql, id);
        if (list.size() > 0) {
            book = new Comment(list.get(0));
        }
        return book;
    }

    @Override
    public boolean delComment(int id) {
        String sql = "delete from comment where id=?";
        int i = DbUtil.excuteUpdate(sql, id);
        return i > 0 ? true : false;
    }

    @Override
    public boolean batDelComment(String ids) {
        String sql = "delete from comment where id in(" + ids + ")";
        int i = DbUtil.excuteUpdate(sql);
        return i > 0 ? true : false;
    }

    /**
     *按商品id获取最新前10条评论
      */
    @Override
    public List<Comment> CommentList(  int id) {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT * from comment c left join book s on c.gid =s.bookId  left join user u on c.uid=u.userId where 1=1 and c.gid=? ORDER BY c.time DESC  limit 0,10 ";
        // 查询的分页结果集
        List<Map<String, Object>> lm = DbUtil.executeQuery(sql, id);
        // 把查询的Comment结果由List<Map<String, Object>>转换为List<Comment>
        if (lm.size() > 0) {
            for (Map<String, Object> map : lm) {
                Comment book = new Comment(map);
                list.add(book);
            }
        }
        return list;
    }

    /**
     * @param
     * @return 返回一个boolean
     */
    @Override
    public boolean commentAdd(Comment comment) {
        String sql="INSERT INTO  comment (comment, send, credible, start, time, uid, gid) VALUES (?,?,?,?,?,?,?)";
        int i= DbUtil.excuteUpdate(sql, comment.getComment(),comment.getSend(),comment.getCredible(),comment.getStart(),comment.getTime(),comment.getUid(),comment.getGid());
        return i>0?true:false;
    }

    @Override
    public boolean updateComment(Comment comment) {
        String sql="update comment set comment=?,send=?,credible=?,start=? where id =?";
        int i=DbUtil.excuteUpdate(sql,comment.getComment(),comment.getSend(),comment.getCredible(),comment.getStart(),comment.getId());
        return i>0?true:false;
    }
}
