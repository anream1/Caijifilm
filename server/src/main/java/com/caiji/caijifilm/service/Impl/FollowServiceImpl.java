package com.caiji.caijifilm.service.Impl;

import com.caiji.caijifilm.dao.FollowDao;
import com.caiji.caijifilm.dao.MovieDao;
import com.caiji.caijifilm.pojo.Movie;
import com.caiji.caijifilm.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowDao followDao;
    @Autowired
    private MovieDao movieDao;

    /**改变一个电影的关注数  服务器传回1则增加关注数，传回0则减少关注数**/
    @Override
    public Movie UpdateMovieFollowNum(int mid , int operand){
        if (operand==1)  movieDao.IncreaseFollowNum(mid);
        else  movieDao.ReduceFollowNum(mid);
        return movieDao.SelectMovieInfoById(mid);
    }

    /**改变用户关注电影**/
    @Override
    public boolean IncreaseFollowMovie(int uid,int mid,int operand){
        boolean tag=false;
        try{
            if(operand==1) followDao.InsertFollowMovie(uid, mid);
            else followDao.DeleteFollowMovie(uid, mid);
            System.out.println("新增成功");
            tag=true;
        }catch(Exception e){
            System.out.println("改变用户关注电影失败!");
            e.printStackTrace();
        }
        return tag;
    }
}
