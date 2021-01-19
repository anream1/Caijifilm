package com.caiji.caijifilm.pojo;
import  java.util.Date;

public class Comment {
    /**获取用户id**/
    User user=new User();
    private int uid=user.getUid();
    /**评论内容**/
    private String content;
    /**获取电影id**/
    Movie movie=new Movie();
    private int mid=movie.getMovieId();
    /**评论时间**/
    private Date CreateTime;
    /**点赞数**/
    private int LNum;
    /**点踩数**/
    private int DLNum;
    /**评论表id**/
    private int ID;

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    /**打分**/
    private int rate;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getId() {
        return ID;
    }

    public void setId(int id) {
        this.ID = id;
    }

    public Comment(){
    }
    /**
     * 获取评论点踩数
     * @return DLNum
     */
    public int getDLNum() {
        return DLNum;
    }
    /**
     * 修改评论点踩数
     * @param DLNum
     */
    public void setDLNum(int DLNum) {
        this.DLNum = DLNum;
    }
    /**
     * 获取评论点赞数
     * @return LNum
     */
    public int getLNum() {
        return LNum;
    }
    /**
     * 修改评论点赞数
     * @param LNum
     */
    public void setLNum(int LNum) {
        this.LNum = LNum;
    }
    /**
     * 获取评论时间
     * @return time
     */
    public Date getTime() {
        return CreateTime;
    }
//    /**
//     * 修改评论时间
//     * @param time
//     */
//    public void setTime(Date time) {
//        this.time = time;
//    }
    /**
     * 获取评论电影id
     * @return mid
     */
    public int getMid() {
        return mid;
    }
    /**
     * 获取评论者id
     * @return uid
     */
    public int getUid() {
        return uid;
    }
    /**
     * 获取评论内容
     * @return content
     */
    public String getContent() {
        return content;
    }
    /**
     * 修改评论
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}
