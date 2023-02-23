package com.dwk.socket;

import cn.hutool.json.JSON;
import com.dwk.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

/**
 * websocket 控制器
 */
@Slf4j
@RestController
public class WebSocketEndPoint {

    @Autowired
    private WebSocketService webSocketService;

    /**
     * 表示服务端可以接收客户端通过主题“/app/hello”发送过来的消息，客户端需要在主题"/topic/hello"上监听并接收服务端发回的消息
     * @param
     * @param
     */
    @MessageMapping("/sendAllConnect")
    @SendTo("/topic/greetings")
    public void greeting(@Payload String message) {
        log.info("客户端消息：" + message);
        webSocketService.responseAll(message,"/topic/greetings");
    }

    /**
     * 这里用的是@SendToUser，发送给单一客户端注解。
     * 服务端和客户端点对点通信方式即限定请求地址：/user /izn5ursn /queue/message
     *                                      前缀  session-ID 【 通信主题  】
     * @return
     */
    @MessageMapping("/message")
    @SendToUser("/queue/message")
    public JSON handleSubscribe(@Payload String message) {
        return webSocketService.pointToPoint(message,"/queue/message");
    }

}
