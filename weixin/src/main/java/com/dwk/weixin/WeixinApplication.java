package com.dwk.weixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author mushi
 */

@SpringBootApplication
@ComponentScan(value = "com.dwk.*")
@EnableFeignClients("com.dwk.*")
@EnableDiscoveryClient
@EnableWebMvc
public class WeixinApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeixinApplication.class, args);
    }

}
