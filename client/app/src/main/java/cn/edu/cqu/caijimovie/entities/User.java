package cn.edu.cqu.caijimovie.entities;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    /**
     * 编号
     */
    private int uid;
    /**
     * 姓名
     */
    private String nickname;
    /**
     * 姓名
     */
    private String password;

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    /**
     * 年龄
     */
    private int age;
    /**
     * 性别
     **/
    private String sex;
    /**
     * 邮箱
     **/
    private String mail;
    /**
     * 电话
     **/
    private String phone;
    /**
     * 上次登录时间
     **/
    private String lasttime;
    /**
     * 头像
     **/
    private byte[] potrait;

    private String userlabel;

    public String getUserLabel() {
        return userlabel;
    }

    public void setUserLabel(String userLabel) {
        this.userlabel = userLabel;
    }


    public User() {
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取姓名
     *
     * @return name
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置姓名
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取年龄
     *
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 获取性别
     *
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取邮箱
     *
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * 设置邮箱
     *
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 获取电话号码
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话号码
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取上次登录时间
     *
     * @return lasttime
     */
    public String getLasttime() {
        return lasttime;
    }

    /**
     * 设置上次登录时间
     *
     * @param lasttime
     */
    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    /**
     * 设置头像
     *
     * @param potrait
     */
    public void setPotrait(byte[] potrait) {
        this.potrait = potrait;
    }

    /**
     * 获取头像
     *
     * @return potrait
     */
    public byte[] getPotrait() {
        return potrait;
    }


}

