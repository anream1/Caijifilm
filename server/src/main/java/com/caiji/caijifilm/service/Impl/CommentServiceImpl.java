package com.caiji.caijifilm.service.Impl;

import com.caiji.caijifilm.dao.CommentDao;
import com.caiji.caijifilm.pojo.Comment;
import com.caiji.caijifilm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl  implements CommentService {
    @Autowired
    private CommentDao commentDao;

    /**根据电影id获取其评论信息**/
    @Override
    public List<Comment> SelectInfoByMid(int mid){return commentDao.SelectInfoByMid(mid);}

    /**根据用户id查询其评论过的所有电影的评论信息**/
    @Override
    public List<Comment> SelectInfoByUid(int uid){return commentDao.SelectInfoByUid(uid);}

    /**检查插入用户评论是否成功**/
    @Override
    public  boolean InsertCommentInfo(String content, int uid, int mid,  int rate)
    {boolean flag=false;
        try{
            commentDao.InsertCommentInfo(content,uid,mid,rate);
            flag=true;
        }catch(Exception e){
            System.out.println("新增失败!");
            e.printStackTrace();
        }
        return flag;
    }

    /**修改用户的点赞或点踩数  客户端传回1则增加点赞数，传回0则增加点踩数**/
    @Override
    public boolean UpdateLNumDLNum(int uid ,int mid,int operand)
    {
        boolean flag=false;
        try{
            if (operand==1) commentDao.IncreaseLNum(uid,mid);
            else commentDao.IncreaseDLNum(uid, mid);
            flag=true;
        }catch(Exception e){
            System.out.println("修改失败!");
            e.printStackTrace();
        }
        return flag;
    }
}
