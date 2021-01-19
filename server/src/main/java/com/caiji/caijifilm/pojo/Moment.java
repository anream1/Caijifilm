package com.caiji.caijifilm.pojo;
//import sun.font.CreatedFontTracker;

import  java.util.Date;

public class Moment {
    /**动态发布者id**/
    User user=new User();
    private int uid=user.getUid();
    /**动态内容**/
    private String content;
    /**动态发布时间**/
    private Date CreateTime;
    /**动态点赞数**/
    private int LNum;
    /**动态点踩数**/

    private int DLNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**动态id**/
    private int id;
    public Moment(){}
    /**
     * 获取动态发布者id
     * @return uid
     */
    public int getUid() {
        return uid;
    }
    /**
     * 修改动态发布者id
     * @param uid
     */
    public void setUid(int uid) {
        this.uid = uid;
    }
    /**
     * 获取动态内容
     * @return content
     */
    public String getContent() {
        return content;
    }
    /**
     * 修改动态内容
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * 获取动态发布时间
     * @return time
     */
    public Date getTime() {
        return CreateTime;
    }
//    /**
//     * 修改动态发布时间
//     * @param time
//     */
//    public void setTime(Date time) {
//        this.time = time;
//    }
    /**
     * 获取动态点赞数
     * @return LNum
     */
    public int getLNum() {
        return LNum;
    }
    /**
     * 修改动态点赞数
     * @param LNum
     */
    public void setLNum(int LNum) {
        this.LNum = LNum;
    }
    /**
     * 获取动态点踩数
     * @return DLNum
     */
    public int getDLNum() {
        return DLNum;
    }
    /**
     * 修改动态点踩数
     * @param DLNum
     */
    public void setDLNum(int DLNum) {
        this.DLNum = DLNum;
    }
}
