package com.caiji.caijifilm.service.Impl;
import java.util.List;

import com.caiji.caijifilm.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caiji.caijifilm.dao.UserDao;
import com.caiji.caijifilm.pojo.User;
import com.caiji.caijifilm.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**添加用户**/
    @Override
    public boolean addUser(User user) {
        boolean flag=false;
        try{
            userDao.addUser(user);
           flag=true;
        }catch(Exception e){
            System.out.println("新增失败!");
            e.printStackTrace();
       }
        return flag;
    }

    /**检测用户邮箱是否存在**/
    @Override
    public Boolean CheckMailexist(String mail){
        boolean flag=false;
      if(userDao.CheckMailexist(mail)==1)
      {
          flag=true;
      }
        return flag;
    }

     /**根据邮箱修改用户信息**/
    @Override
    public boolean updateUser(User user) {
        boolean flag=true;
        try{
            userDao.updateUser(user);
            flag=false;
        }catch(Exception e){
            System.out.println("修改失败!");
            e.printStackTrace();
        }
        return flag;
    }

    //用户头像修改
    @Override
    public boolean updatePortait(User user) {
        boolean flag=true;
        try{
            userDao.updatePotrait(user);
            flag=false;
        }catch(Exception e){
            System.out.println("修改失败!");
            e.printStackTrace();
        }
        return flag;
    }

    //更新用户登录时间
    @Override
    public boolean updateLastTime(User user) {
        boolean flag=true;
        try{
            userDao.updateLastTime(user);
            flag=false;
        }catch(Exception e){
            System.out.println("修改失败!");
            e.printStackTrace();
        }
        return flag;
    }

    /**根据用户邮箱查询用户信息**/
    @Override
    public User findUserByMail(String mail) {
        return userDao.findByMail(mail);
    }

    /**登陆**/
    @Override
    public String LoginCheck(String mail){
        return userDao.LoginCheck(mail);
    }

    /***查询用户表中最后一条记录的id*/
    @Override
    public int  SeclectLastRecord(){
        return userDao.SeclectLastRecord();
    }

    /**更新user表里的用户自选标签**/
    @Override
    public boolean UpdateUserLabel(int uid ,String userlabel) {
        boolean flag=false;
        try{
            userDao.UpdateUserLabel(uid, userlabel);
            flag=true;
        }catch(Exception e){
            System.out.println("修改失败!");
            e.printStackTrace();
        }
        return flag;
    }
}
