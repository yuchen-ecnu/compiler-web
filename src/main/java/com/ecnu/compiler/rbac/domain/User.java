package com.ecnu.compiler.rbac.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Michael Chen
 */
@TableName("user")
public class User  extends Model<User> {
    private int id;
    private String nickName;
    private String pwd;
    private String email;
    private Integer userType;
    private Integer volume;
    private Timestamp gmtCreated;
    private Timestamp gmtModified;
    private Timestamp lastLogin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Timestamp getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Timestamp gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isValid(){
        return !(ObjectUtils.isEmpty(email)
                || ObjectUtils.isEmpty(pwd));
    }

    /**
     * check password is correct or not
     * @param pwd 明文
     * @return
     */
    public boolean checkPassword(String pwd){
        return !ObjectUtils.isEmpty(pwd)
                && DigestUtils.md5DigestAsHex(pwd.getBytes()).equalsIgnoreCase(this.pwd);
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
