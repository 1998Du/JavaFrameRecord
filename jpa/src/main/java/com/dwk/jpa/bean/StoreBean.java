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
 * @Date: 2021/3/16 14:38
 */
@Component
@Entity
@Table(name = "store")
public class StoreBean {

    /**店铺id*/
    @Id
    @Column(name = "id")
    private String id;
    /**店铺名*/
    @Column(name = "store_name")
    private String storeName;
    /**用户名*/
    @Column(name = "user_name")
    private String userName;
    /**电话*/
    @Column(name = "phone")
    private String phone;
    /**店铺地址*/
    @Column(name = "store_address")
    private String storeAddress;
    /**店铺描述*/
    @Column(name = "description")
    private String description;
    /**主体照片*/
    @Column(name = "img_head")
    private String imgHead;
    /**副图*/
    @Column(name = "img_body1")
    private String imgBody1;
    /**副图*/
    @Column(name = "img_body2")
    private String imgBody2;
    /**副图*/
    @Column(name = "img_body3")
    private String imgBody3;
    /**副图*/
    @Column(name = "img_body4")
    private String imgBody4;
    /**副图*/
    @Column(name = "img_body5")
    private String imgBody5;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgHead() {
        return imgHead;
    }

    public void setImgHead(String imgHead) {
        this.imgHead = imgHead;
    }

    public String getImgBody1() {
        return imgBody1;
    }

    public void setImgBody1(String imgBody1) {
        this.imgBody1 = imgBody1;
    }

    public String getImgBody2() {
        return imgBody2;
    }

    public void setImgBody2(String imgBody2) {
        this.imgBody2 = imgBody2;
    }

    public String getImgBody3() {
        return imgBody3;
    }

    public void setImgBody3(String imgBody3) {
        this.imgBody3 = imgBody3;
    }

    public String getImgBody4() {
        return imgBody4;
    }

    public void setImgBody4(String imgBody4) {
        this.imgBody4 = imgBody4;
    }

    public String getImgBody5() {
        return imgBody5;
    }

    public void setImgBody5(String imgBody5) {
        this.imgBody5 = imgBody5;
    }
}
