package com.dwk.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author dwk
 * @date 2024/5/30  14:50
 * @description 被监听的事件
 */
public class MyOrderEvent extends ApplicationEvent {
    public MyOrderEvent(Object source) {
        super(source);
    }
}
