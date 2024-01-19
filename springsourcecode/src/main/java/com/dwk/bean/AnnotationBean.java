package com.dwk.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author duweikun
 * @date 2024/1/18  16:01
 * @description
 */
@Component
public class AnnotationBean {

    @Bean
    public User getUser(){
        return new User();
    }

}
