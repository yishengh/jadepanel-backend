package com.jade.user.entity;

import java.io.Serializable;

/**
 * mm财务管理-用户(UserInfo)实体类
 *
 * @author makejava
 * @since 2021-02-23 11:15:23
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 958506210919059476L;

    public UserInfo() {
    }

    public UserInfo(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    /**
    * 主键,用户id
    */
    private Integer userId;
    /**
    * 用户名
    */
    private String userName;
    /**
    * 密码
    */
    private String userPassword;
    /**
    * 性别
    */
    private String sex;
    /**
    * 生日
    */
    private String birthday;
    
    private String email;
    
    private String qq;
    /**
    * 角色(admin或uer)
    */
    private String role;
    /**
    * 是否存活（0:未存活  1:已存活）
    */
    private Integer isactive;
    /**
    * 创建日期
    */
    private String createdate;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getIsactive() {
        return isactive;
    }

    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

}