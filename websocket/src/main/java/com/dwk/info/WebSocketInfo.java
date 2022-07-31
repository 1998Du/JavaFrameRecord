package com.dwk.info;

import cn.hutool.json.JSON;

public interface WebSocketInfo {

    /**点对点消息处理*/
    JSON pointToPoint(String message, String topic);

    /**定时广播消息处理*/
    void responseAllByScheduled();

    void responseAll(String message,String topic);

}
