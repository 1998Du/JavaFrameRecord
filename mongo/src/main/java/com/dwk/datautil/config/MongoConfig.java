package com.dwk.datautil.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/4/1 11:03
 */
@Component
@ConfigurationProperties(prefix = "dwk.mongo")
public class MongoConfig {

    /**mongo ip*/
    private List<String> ip;

    /**mongo port*/
    private List<Integer> port;

    /**数据库名*/
    private List<String> dataBase;

    /**文档集*/
    private List<String> collection;

    /**用户名*/
    private List<String> userName;

    /**密码*/
    private List<String> password;

    /**最大等待时间*/
    private String maxWaitTime;


    public List<String> getIp() {
        return ip;
    }

    public void setIp(List<String> ip) {
        this.ip = ip;
    }

    public List<Integer> getPort() {
        return port;
    }

    public void setPort(List<Integer> port) {
        this.port = port;
    }

    public List<String> getDataBase() {
        return dataBase;
    }

    public void setDataBase(List<String> dataBase) {
        this.dataBase = dataBase;
    }

    public List<String> getCollection() {
        return collection;
    }

    public void setCollection(List<String> collection) {
        this.collection = collection;
    }

    public List<String> getUserName() {
        return userName;
    }

    public void setUserName(List<String> userName) {
        this.userName = userName;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public String getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(String maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    /**配置的mongo的database*/
    @Bean
    public Map<String,MongoDatabase> databaseMap(){
        Map<String,MongoDatabase> dbMap = new HashMap<>();
        for (int i = 0 ; i<this.getIp().size() ; i++){
            MongoClient mongoClient = null;
            MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
            builder.maxWaitTime(Integer.valueOf(this.getMaxWaitTime())*1000);
            MongoClientOptions options = builder.build();
            ServerAddress serverAddress = new ServerAddress(this.getIp().get(i), this.getPort().get(i));

            boolean userNameIsNull = ("").equals(this.getUserName().get(i)) || this.getUserName().get(i) == null;
            boolean passWordIsNull = ("").equals(this.getPassword().get(i)) || this.getPassword().get(i) == null;

            if (userNameIsNull && !passWordIsNull){
                throw new RuntimeException("缺少用户名");
            }

            if (!userNameIsNull && passWordIsNull){
                throw new RuntimeException("缺少密码");
            }

            if (userNameIsNull && passWordIsNull){
                mongoClient = new MongoClient(serverAddress,options);
            }else{
                MongoCredential scramSha1Credential = MongoCredential.createScramSha1Credential(this.getUserName().get(i), this.getDataBase().get(i), this.getPassword().get(i).toCharArray());
                mongoClient = new MongoClient(serverAddress,scramSha1Credential,options);
            }
            MongoDatabase database = mongoClient.getDatabase(this.getDataBase().get(i));
            dbMap.put(this.getDataBase().get(i),database);
        }
        return dbMap;
    }

}
