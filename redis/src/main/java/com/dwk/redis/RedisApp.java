package com.dwk.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/11 9:24
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value = "com.dwk.*")
@EnableFeignClients("com.dwk.*")
public class RedisApp {

    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class,args);
    }

}
