package com.dwk.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "filepath")
public class PathConfigProperties {


    /**源路径*/
    private List<String> sourcePath;

    /**目标路径*/
    private String targetPath;

    /**定时时间*/
    private String cron;

    /**是否开启*/
    private boolean open;

    public List<String> getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(List<String> sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
