package com.caiji.caijifilm.service.Impl;

import com.caiji.caijifilm.dao.MomentDao;
import com.caiji.caijifilm.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.caiji.caijifilm.pojo.Moment;

import java.util.Date;
import java.util.List;


@Service
public class MomentServiceImpl implements MomentService {
    @Autowired
    private MomentDao momentDao;

    /**根据用户id查询其所有动态信息**/
    @Override
    public List<Moment> SelectInfoByUid(int uid){return momentDao.SelectMomentByUid(uid);}

    /**检查插入用户动态是否成功**/
    @Override
    public  boolean InsertMomentInfo(String content, int uid)
    {boolean flag=false;
        try{
            momentDao.InsertMomentInfo(content,uid);
            flag=true;
        }catch(Exception e){
            System.out.println("新增失败!");
            e.printStackTrace();
        }
        return flag;
    }


//    /**检查插入用户动态是否成功**/
//    @Override
//    public  boolean InsertMomentInfo(Moment moment)
//    {boolean flag=false;
//        try{
//            momentDao.InsertMomentInfo(moment);
//            flag=true;
//        }catch(Exception e){
//            System.out.println("新增失败!");
//            e.printStackTrace();
//        }
//        return flag;
//    }

    /**修改用户的点赞或点踩数  客户端传回1则增加点赞数，传回0则增加点踩数**/
    @Override
    public boolean UpdateLNumDLNum(int id,int operand)
    {
        boolean tag=false;
        try{
            if (operand==1) momentDao.IncreaseLNum(id);
            else momentDao.IncreaseDLNum(id);
            tag=true;
        }catch(Exception e){
            System.out.println("修改失败!");
            e.printStackTrace();
        }
        return tag;
    }
}
