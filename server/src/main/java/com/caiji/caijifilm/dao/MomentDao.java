package com.caiji.caijifilm.dao;

import  com.caiji.caijifilm.pojo.Moment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface MomentDao {

    /**根据用户id查询其所有动态**/
    @Select("select * from moment where uid=#{uid} order by Createtime desc")
    List<Moment> SelectMomentByUid(int uid);

    /**插入用户动态**/
    @Insert("insert into moment (content,uid) values (#{content},#{uid})")
    void InsertMomentInfo(String content, int uid);

    /**增加点赞数**/
    @Update("update moment set lnum = lnum + 1 where id=#{id}")
    void IncreaseLNum(int id);

    /**增加点踩数**/
    @Update("update moment set dlnum = dlnum + 1 where id=#{id}")
    void IncreaseDLNum(int id);

    /**返回最后一条记录的id**/
    @Select("select id from moment order by id desc limit 1")
    int Selectlastid();
}
