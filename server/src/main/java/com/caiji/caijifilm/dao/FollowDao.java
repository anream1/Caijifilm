package com.caiji.caijifilm.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
@Mapper
public interface FollowDao {

    /**增加用户关注的电影**/
    @Insert("insert into follower (uid,mid) values (#{uid},#{mid})")
    void InsertFollowMovie(int uid,int mid);

    /**删除用户关注的电影**/
    @Delete("delete from follower where uid =#{uid} and mid=#{mid}")
    void DeleteFollowMovie(int uid,int mid);

    /**查询用户关注的电影id**/
    @Select("select mid from follower where uid=#{uid}")
    List<Integer> SelectFollowMovie(int uid);

    /**查询关注同一部电影的用户id**/
    @Select("select uid from follower where mid=#{mid}")
    List<Integer> SelectFollowUser(int uid);

    /**查询用户是否关注过该电影**/
    @Select("SELECT EXISTS(SELECT * FROM follower WHERE uid =#{uid} and mid=#{mid})")
    boolean CheckFollow(int uid,int mid);
}
