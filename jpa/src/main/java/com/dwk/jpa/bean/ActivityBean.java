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
 * @Date: 2021/3/16 14:45
 */
@Component
@Entity
@Table(name = "activity")
public class ActivityBean {

    /**活动id*/
    @Id
    @Column(name = "id")
    private String id;
    /**活动描述*/
    @Column(name = "description")
    private String description;
    /**活动时间*/
    @Column(name = "time")
    private String time;
    /**活动地点*/
    @Column(name = "address")
    private String address;
    /**所属店铺id*/
    @Column(name = "belong_store_id")
    private String belongStoreId;
    /**活动图片*/
    @Column(name = "img")
    private String img;
    /**活动是偶过期*/
    @Column(name = "timeout")
    private String timeout;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBelongStoreId() {
        return belongStoreId;
    }

    public void setBelongStoreId(String belongStoreId) {
        this.belongStoreId = belongStoreId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
