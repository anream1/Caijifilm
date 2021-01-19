package com.caiji.caijifilm.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedbackDao {

    /**将用户反馈插入表feedback中**/
    @Insert("insert into feedback (uid,feedback) values (#{uid},#{feedback})")
    void InsertFeedback(int uid,String feedback);
}

