package com.dwk.datautil.bean;

import org.springframework.stereotype.Component;

/**
 * 
 * Description:
 *  存储文档索引
 * @author: mushi
 * @Date: 2021/4/1 16:52
 */
@Component
public class BaseDataBean {

    private String id;

    private String queryName;

    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
