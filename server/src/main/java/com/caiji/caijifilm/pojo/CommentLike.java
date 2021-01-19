package com.caiji.caijifilm.pojo;

import java.util.Date;

public class CommentLike {

    /**评论表id**/
    private int cid;
    /**评论者id**/
   private int cuid;
   /**电影id**/
    private int mid;
    /**查看电影评论者是否给某条评论点过赞**/
    private boolean like;
    /**点赞数**/
    private int lnum;
    /**评论者打分**/
    private int rate;
    /**评论时间**/
    private Date time;
    /**评论内容**/
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

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
