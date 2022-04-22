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
 * @Date: 2021/3/16 14:26
 */
@Component
@Entity
@Table(name = "identity")
public class IdentityBean {

    /**身份id*/
    @Id
    @Column(name = "id")
    private String id;
    /**身份名称*/
    @Column(name = "identity")
    private String identity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
