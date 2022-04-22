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
 * @Date: 2021/3/16 14:44
 */
@Component
@Entity
@Table(name = "notice   ")
public class NoticeBean {

    /**公告id*/
    @Id
    @Column(name = "id")
    private String id;
    /**公告内容*/
    @Column(name = "content")
    private String content;
    /**发布人*/
    @Column(name = "user_name")
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
