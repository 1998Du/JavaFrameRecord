package com.dwk.listen;

import com.dwk.event.MyOrderEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author dwk
 * @date 2024/5/30  14:47
 * @description  监听器
 */
public class MyListener implements ApplicationListener<MyOrderEvent> {

    @Override
    public void onApplicationEvent(MyOrderEvent myOrder) {
        // 监听到事件之后的处理逻辑
        System.out.println(this.getClass().getSimpleName() + "监听到了事件，开始处理.");
    }
}
