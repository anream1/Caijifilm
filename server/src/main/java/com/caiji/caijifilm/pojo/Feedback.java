package com.caiji.caijifilm.pojo;

import java.util.Date;

public class Feedback {
    User user=new User();

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    /**反馈者id**/
    private int uid=user.getUid();
    /**反馈内容**/
    private String feedback;
    /**反馈时间**/
    private Date time;
}
