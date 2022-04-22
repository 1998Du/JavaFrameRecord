package com.dwk.datautil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 
 * Description:
 *
 * @author: mushi
 * @Date: 2021/4/1 10:41
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MongoDataUtilApp {

    public static void main(String[] args) {
        SpringApplication.run(MongoDataUtilApp.class,args);
    }

}
