package com.dwk.socket.manager;

import com.dwk.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * websocket 连接管理
 */
@Slf4j
@Component
public class WebSocketManager implements WebSocketHandlerDecoratorFactory {

    @Autowired
    private WebSocketService webSocketService;

    /**存储连接*/
    private static final ConcurrentHashMap<String,WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    /**连接统计*/
    private static final AtomicLong size = new AtomicLong();

    /**添加*/
    public static void addSession(String key, WebSocketSession webSocketSession){
        log.info("添加websocket连接，key = {}，session = {}",key,webSocketSession);
        size.compareAndSet(1,1);
        sessionMap.put(key,webSocketSession);
    }

    /**移除*/
    public static void removeSession(String key){
        log.info("移除websocket连接，key = {}",key);
        sessionMap.remove(key);
    }

    /**获取*/
    public static WebSocketSession getSession(String key){
        if (sessionMap.contains(key)){
            return sessionMap.get(key);
        }
        throw new RuntimeException("该session不存在");
    }


    /**websocket连接握手，此处用于监听连接是否成功*/
    @Override
    public WebSocketHandler decorate(WebSocketHandler webSocketHandler) {
        return new WebSocketHandlerDecorator(webSocketHandler) {
            /**连接建立成功后执行*/
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                log.info("websocket connection key = {},request uri = {},request from = {}",session.getId(),session.getUri(),session.getRemoteAddress());
                webSocketService.responseAll(session.getRemoteAddress() + "连接成功!","/topic/greetings");
                //此处可以根据session中缓存的身份验证校验session
                addSession(session.getId(),session);
                super.afterConnectionEstablished(session);
            }
            /**连接关闭后执行*/
            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                log.info("websocket close key = {},uri = {},request from = {}",session.getId(),session.getUri(),session.getRemoteAddress());
                removeSession(session.getId());
                super.afterConnectionClosed(session, closeStatus);
            }
        };
    }

}
