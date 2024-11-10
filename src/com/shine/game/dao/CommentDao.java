package com.shine.game.dao;

import com.shine.game.bean.Comment;
import com.shine.game.bean.PageBean;
import com.shine.game.bean.User;

import java.util.List;

/**
* @version
*/
public interface CommentDao {

	// 获取评论总数
	long commentReadCount();

	//获取评论列表
	List<Comment> commentList(PageBean pageBean);

	//查找当前用户是否对此商品评价过
	boolean findCommentByUid(Integer gid,Integer uid);

	//根据id获取一个评论的信息
	Comment findComment(Integer id);

	//根据id删除一个评论
	boolean delComment(int id);

	//根据一组id字符串批量删除评论
	boolean batDelComment(String ids);
	//添加评论
	boolean commentAdd(Comment comment);

	//更新评论
	boolean updateComment(Comment comment);
	// 按商品id获取最新前10条评论
	List<Comment> CommentList(  int id);
}
