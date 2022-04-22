package com.dwk.design.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 *      开发者相关配置信息
 * @author: mushi
 * @Date: 2021/2/2 9:28
 */
@Component
@ConfigurationProperties(prefix = "ali.config")
public class AliConfig {
    private static volatile AliConfig aliConfig = null;

    /**地域节点*/
    private String endpoint;
    /**账号*/
    private String accessKeyId;
    /**密码*/
    private String accessKeySecret;
    /**存储空间*/
    private List<String> bucketName;

    private AliConfig (){

    }

    public static AliConfig getAliConfig() {
        if (aliConfig == null){
            aliConfig = new AliConfig();
            System.out.println(aliConfig.getAccessKeyId()+aliConfig.getEndpoint());
        }
        return aliConfig;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public List<String> getBucketName() {
        return bucketName;
    }

    public void setBucketName(List<String> bucketName) {
        this.bucketName = bucketName;
    }
}
