package com.caiji.caijifilm.service;

public interface FeedbackService {

    /**检查插入用户反馈是否成功**/
    boolean InsertFeedback(int uid,String feedback);
}
