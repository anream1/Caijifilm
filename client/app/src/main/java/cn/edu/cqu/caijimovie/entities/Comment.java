package cn.edu.cqu.caijimovie.entities;

import java.util.Date;

public class Comment {
    /**
     * 评论表id
     **/
    private int cid;
    /**
     * 评论者id
     **/
    private int cuid;
    /**
     * 电影id
     **/
    private int mid;
    /**
     * 点赞数
     **/
    private int lnum;
    /**
     * 评论者打分
     **/
    private int rate;
    /**
     * 评论时间
     **/
    private String time;
    /**
     * 评论内容
     **/
    private String content;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCuid() {
        return cuid;
    }

    public void setCuid(int cuid) {
        this.cuid = cuid;
    }


    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }


    public int getLnum() {
        return lnum;
    }

    public void setLnum(int lnum) {
        this.lnum = lnum;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
