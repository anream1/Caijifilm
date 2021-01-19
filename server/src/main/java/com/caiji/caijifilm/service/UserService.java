package com.caiji.caijifilm.service;
import java.util.List;
import com.caiji.caijifilm.pojo.User;

public interface UserService {
    /**
     * 新增用户
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 更新用户登录时间
     * @param user
     * @return
     */
    boolean updateLastTime(User user);

    /**
     * 用户头像修改
     * @param user
     * @return
     */
    boolean updatePortait(User user);

    /**
     * 根据用户邮箱查询用户信息
     * @param mail
     */
    User findUserByMail(String mail);

    /**
     * 登陆检查
     */
    String LoginCheck(String mail);

    /**
     * 根据邮箱输入判断用户邮箱是否被注册
     */
    Boolean CheckMailexist(String mail);

    /**
     * 查询用户表中的最后一条记录的id
     */
    int SeclectLastRecord();

    /**
     * 修改用户自选标签
     */
    boolean UpdateUserLabel(int uid,String userlabel);
}


