package com.dwk.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosApp {

    public static void main(String[] args) {
        SpringApplication.run(NacosApp.class);
    }

}
