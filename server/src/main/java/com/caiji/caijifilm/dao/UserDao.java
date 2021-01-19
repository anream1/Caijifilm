package com.caiji.caijifilm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.caiji.caijifilm.pojo.User;
@Mapper
public interface UserDao {


    /**
     * 用户数据修改
     */
    @Update("update userinfo set NickName=#{nickname},Sex=#{sex},Phone=#{phone} where Mail=#{mail}")
    void updateUser(User user);

    /**
     * 更新用户登录时间
     */
    @Update("update userinfo set LastTime=#{lasttime} where Mail=#{mail}")
    void updateLastTime(User user);

    /*
     * 用户头像修改
     */
    @Update("update userinfo set Potrait=#{potrait} where Mail=#{mail}")
    void updatePotrait(User user);


    /**
     * 根据用户邮箱查询用户信息
     *
     */
    @Select("SELECT UID,NickName,Sex,Potrait,Mail,Phone,Password FROM userinfo where mail=#{mail}")
    User findByMail(String mail);


    /**
     * 登陆查询
     */
     @Select("SELECT Password where mail=#{mail}")
    String LoginCheck(String  mail);


     /**
      * 用户数据新增
      */
    @Insert("insert into userinfo(UID,nickname,Mail,Password,LastTime) values (#{uid},#{nickname},#{mail},#{password},#{lasttime})")
    void addUser(User user);


    /**
     * 查询用户邮箱是否存在
     */
    @Select("SELECT EXISTS(SELECT * FROM userinfo WHERE mail =#{mail})")
    int CheckMailexist(String mail);

    /**
     * 查询表中最后一条记录的id
     */
    @Select("select Uid from userinfo order by Uid desc limit 1")
     int  SeclectLastRecord() ;

    /**
     * 用户更新其自选标签
     */
    @Update("update userinfo set userlabel=#{userlabel} where uid=#{uid}")
    void UpdateUserLabel(int uid,String userlabel);

}