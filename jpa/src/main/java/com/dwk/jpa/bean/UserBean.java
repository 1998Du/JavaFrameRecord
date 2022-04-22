package com.dwk.jpa.bean;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/16 14:20
 */
@Component
@Entity
@Table(name = "user")
public class UserBean {

    /**用户名*/
    @Id
    @Column(name = "user_name")
    private String userName;
    /**密码*/
    @Column(name = "pass_word")
    private String passWord;
    /**是否可以通行*/
    @Column(name = "pass")
    private String pass;
    /**身份id*/
    @Column(name = "identity")
    private String identity;
    /**是否审核*/
    @Column(name = "check")
    private String check;
    /**微信公众号openId*/
    @Column(name = "open_id")
    private String openId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
