package com.dwk.design;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Description:
 *
 * @author: mushi
 * @Date: 2021/2/2 10:37
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value = "com.dwk.*")
@EnableFeignClients("com.dwk.*")
public class OssApp {
    public static void main(String[] args) {
        SpringApplication.run(OssApp.class,args);
    }
}
