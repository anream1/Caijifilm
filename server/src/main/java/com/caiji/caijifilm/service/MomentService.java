package com.caiji.caijifilm.service;

import com.caiji.caijifilm.pojo.Moment;

import java.util.Date;
import java.util.List;

public interface MomentService {

    /**根据用户id查询其动态信息**/
     List<Moment> SelectInfoByUid(int uid);

    /**检查插入用户动态是否成功**/
    boolean InsertMomentInfo(String content, int uid);

//    /**检查插入用户动态是否成功**/
//    public  boolean InsertMomentInfo(Moment moment);

    /**修改用户的点赞或点踩数  客户端传回1则增加点赞数，传回0则增加点踩数**/
     boolean UpdateLNumDLNum(int id,int operand);
}
