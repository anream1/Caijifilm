package com.caiji.caijifilm.service;

import com.caiji.caijifilm.pojo.Comment;

import java.util.Date;
import java.util.List;

public interface CommentService {

    /**根据电影id获取其评论信息**/
     List<Comment> SelectInfoByMid(int mid);

    /**根据用户id查询其评论过的所有电影的评论信息**/
     List<Comment> SelectInfoByUid(int uid);

    /**检查插入用户评论是否成功**/
    boolean InsertCommentInfo(String content, int uid, int mid,int rate);

    /**修改用户的点赞或点踩数  客户端传回1则增加点赞数，传回0则增加点踩数**/
     boolean UpdateLNumDLNum(int uid ,int mid,int operand);
}
