package com.dwk.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CommonApplicationContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CommonApplicationContext.applicationContext = applicationContext;
    }

    public static Object getBean(Class object){
        return applicationContext.getBean(object);
    }
}
