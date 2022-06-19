package com.dwk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FileBackApp {
    public static void main(String[] args) {
        SpringApplication.run(FileBackApp.class,args);
    }
}
