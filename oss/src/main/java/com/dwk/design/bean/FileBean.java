package com.dwk.design.bean;

import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/10 14:46
 */
@Component
public class FileBean {

    /**文件名*/
    private String fileName;
    /**文件路径*/
    private String path;
    /**文件类型*/
    private String type;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
