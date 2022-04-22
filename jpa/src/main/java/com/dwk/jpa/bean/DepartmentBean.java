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
 * @Date: 2021/3/16 14:37
 */
@Component
@Entity
@Table(name = "department")
public class DepartmentBean {

    /**院系id*/
    @Id
    @Column(name = "id")
    private String id;
    /**院系名称*/
    @Column(name = "name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
