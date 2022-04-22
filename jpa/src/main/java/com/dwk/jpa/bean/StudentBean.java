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
 * @Date: 2021/3/16 14:28
 */
@Component
@Entity
@Table(name = "student")
public class StudentBean {

    /**学号*/
    @Id
    @Column(name = "stu_num")
    private String stuNum;
    /**姓名*/
    @Column(name = "name")
    private String name;
    /**年级*/
    @Column(name = "grade")
    private String grade;
    /**性别*/
    @Column(name = "sex")
    private String sex;
    /**用户名*/
    @Column(name = "user_name")
    private String userName;
    /**专业*/
    @Column(name = "major")
    private String major;
    /**院系*/
    @Column(name = "department")
    private String department;
    /**qq*/
    @Column(name = "qq")
    private String qq;
    /**微信*/
    @Column(name = "wei_xin")
    private String weiXin;
    /**电话*/
    @Column(name = "phone")
    private String phone;
    /**身份证号*/
    @Column(name = "identity_num")
    private String identityNum;

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }
}
