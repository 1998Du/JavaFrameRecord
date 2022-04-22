package com.dwk.jpa;

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
 * @Date: 2021/3/16 15:08
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value = "com.dwk.*")
@EnableFeignClients("com.dwk.*")
public class JpaApp {
    public static void main(String[] args) {
        SpringApplication.run(JpaApp.class,args);
    }
}
