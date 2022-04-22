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
 * @Date: 2021/3/16 14:34
 */
@Component
@Entity
@Table(name = "major")
public class MajorBean {

    /**专业编号*/
    @Id
    @Column(name = "id")
    private String id;
    /**专业名*/
    @Column(name = "name")
    private String name;
    /**专业描述*/
    @Column(name = "description")
    private String description;
    /**所属院系编号*/
    @Column(name = "department_id")
    private String departmentId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
