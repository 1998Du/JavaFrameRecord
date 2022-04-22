package com.dwk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimeTaskApp {

    public static void main(String[] args) {
        SpringApplication.run(TimeTaskApp.class,args);
    }

}
