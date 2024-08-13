package com.dwk;


import com.dwk.event.MyOrderEvent;
import com.dwk.listen.MyListener;
import com.sun.rmi.rmid.ExecPermission;
import org.junit.Test;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * @author dwk
 * @date 2024/5/30  14:43
 * @description
 */
public class Demo {

    // 多播器  发布订阅模式
    @Test
    public void testMulticaster(){
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        // 注册监听器,注册几个就执行几个
        multicaster.addApplicationListener(new MyListener());
        multicaster.addApplicationListener(new MyListener());
        multicaster.addApplicationListener(new MyListener());
        // 注册bean
        // multicaster.addApplicationListenerBean();
        // 发布事件
        multicaster.multicastEvent(new MyOrderEvent(this));
    }

    // 国际化
    @Test
    public void testInternationalAlization(){
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        // 设置国际化文件位置
        resourceBundleMessageSource.setBasename("i18n/message");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setDefaultLocale(Locale.CHINA);
        String message = resourceBundleMessageSource.getMessage("hello",new Object[]{"TOM"},Locale.getDefault());
        System.out.println(message);
    }

    // 表达式
    @Test
    public void testSpEL(){

    }
}
