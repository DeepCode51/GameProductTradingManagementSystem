package com.shine.game.bean;

import java.util.Date;
import java.util.Map;

/**
 * 评价
 */
public class Comment {

    private int id;         // 编号
    private String comment; //内容
    private String send;    //发货速度
    private String credible;//可信度
    private String start;   //评级
    private Date time;    //评价时间
    private int uid;        //用户id
    private int gid;        //商品id
    private User user = new User(); // 买家用户
    private Book book = new Book();//商品

    public Comment() {
    }

    public Comment(String comment, String send, String credible, String start, Date time, int uid, int gid) {
        this.comment = comment;
        this.send = send;
        this.credible = credible;
        this.start = start;
        this.time = time;
        this.uid = uid;
        this.gid = gid;
    }

    // 这里是从数据库获取时集合转对象
    public Comment(Map<String, Object> map) {
        this.id = (int) map.get("id");
        this.comment = (String) map.get("comment");
        this.send = (String) map.get("send");
        this.credible = (String) map.get("credible");
        this.start = (String) map.get("start");
        this.time = (Date) map.get("time");
        this.user = new User(map);
        this.book = new Book(map);
    }

    public Comment(int id, String comment, String send, String credible, String start) {
        this.comment = comment;
        this.send = send;
        this.credible = credible;
        this.start = start;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getCredible() {
        return credible;
    }

    public void setCredible(String credible) {
        this.credible = credible;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", send='" + send + '\'' +
                ", credible='" + credible + '\'' +
                ", start='" + start + '\'' +
                ", time=" + time +
                ", uid=" + uid +
                ", gid=" + gid +
                ", user=" + user +
                ", book=" + book +
                '}';
    }
}
