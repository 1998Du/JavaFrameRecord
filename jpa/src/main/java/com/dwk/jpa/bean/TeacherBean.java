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
 * @Date: 2021/3/16 14:31
 */
@Component
@Entity
@Table(name = "teacher")
public class TeacherBean {

    /**工号*/
    @Id
    @Column(name = "work_num")
    private String workNum;
    /**姓名*/
    @Column(name = "name")
    private String name;
    /**所属院系*/
    @Column(name = "department_id")
    private String departmentId;
    /**qq*/
    @Column(name = "qq")
    private String qq;
    /**weixin*/
    @Column(name = "wei_xin")
    private String weiXin;
    /**电话*/
    @Column(name = "phone")
    private String phone;
    /**用户名*/
    @Column(name = "user_name")
    private String userName;
    /**职称*/
    @Column(name = "level")
    private String level;

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeiXin() {
        return weiXin;
    }

    public void setWeiXin(String weiXin) {
        this.weiXin = weiXin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
