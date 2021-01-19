package com.caiji.caijifilm.service.Impl;

import com.caiji.caijifilm.dao.FeedbackDao;
import com.caiji.caijifilm.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;


    /**检查插入用户反馈是否成功**/
    @Override
    public  boolean InsertFeedback(int uid,String feedback)
    {boolean flag=false;
        try{
            feedbackDao.InsertFeedback(uid, feedback);
            flag=true;
        }catch(Exception e){
            System.out.println("新增失败!");
            e.printStackTrace();
        }
        return flag;
    }
}
