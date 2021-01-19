package com.caiji.caijifilm.service;

import com.caiji.caijifilm.pojo.Movie;
import com.caiji.caijifilm.pojo.Follow;

import java.util.List;

public interface FollowService {

    /**改变一个电影的关注数**/
     Movie UpdateMovieFollowNum(int mid , int operand);

    /**改变用户关注电影**/
     boolean IncreaseFollowMovie(int uid,int mid,int operand);
}
