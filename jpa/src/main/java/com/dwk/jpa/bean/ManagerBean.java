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
 * @Date: 2021/3/16 14:42
 */
@Component
@Entity
@Table(name = "manager")
public class ManagerBean {

    /**管理员id*/
    @Id
    @Column(name = "id")
    private String id;
    /**用户名*/
    @Column(name = "user_name")
    private String userName;
    /**姓名*/
    @Column(name = "name")
    private String name;
    /**电话*/
    @Column(name = "phone")
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
