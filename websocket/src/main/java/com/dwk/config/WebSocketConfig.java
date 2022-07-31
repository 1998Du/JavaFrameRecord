package com.dwk.config;

import com.dwk.properties.WebSocketProperties;
import com.dwk.socket.manager.WebSocketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

/**
 * websocket配置
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Autowired
    private WebSocketProperties webSocketProperties;

    @Autowired
    private WebSocketManager webSocketManager;

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 用户发送请求url="127.0.0.1:12003/socket"与STOMP server进行连接。之后再转发到订阅url；
     * PS：端点的作用——客户端在订阅或发布消息到目的地址前，要连接该端点。
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {

        List<String> endPointLiist = webSocketProperties.getEndPoint();
        log.error(applicationName + "服务  websocket连接端点:" + endPointLiist);
        if (endPointLiist.size() == 0 || endPointLiist == null){
            throw new RuntimeException("请先配置websocket连接端点！websocket.endPoint");
        }
        String[] endPoints = endPointLiist.stream().toArray(String[]::new);
        //设置websocket连接端点
        //stompEndpointRegistry.addEndpoint(endPoints).setAllowedOrigins("*").withSockJS();
        //spring boot 2.4以上版本使用此行配置
        stompEndpointRegistry.addEndpoint(endPoints).setAllowedOriginPatterns("*").withSockJS();

    }

    /**
     * 配置了一个简单的消息代理，如果不重载，默认情况下会自动配置一个简单的内存消息代理，用来处理以"/topic"为前缀的消息。这里重载configureMessageBroker()方法，
     * 消息代理将会处理前缀为"/topic"和"/queue"的消息。
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        List<String> websocketPrefixList = webSocketProperties.getWebsocketPrefix();
        log.error(applicationName + "服务  websocket前缀:" + websocketPrefixList);
        if (websocketPrefixList == null || websocketPrefixList.size() == 0){
            throw new RuntimeException("请先配置websocket连接前缀：websocket.websocketPrefix");
        }
        //1、前端请求需要在请求前加上setApplicationDestinationPrefixes内设置的值，即socket连接前缀
        String[] websocketPrefixs = websocketPrefixList.stream().toArray(String[]::new);
        registry.setApplicationDestinationPrefixes(websocketPrefixs);
        //2、消息代理前缀，如果消息的前缀为"/topic"、"/queue"，就会将消息转发给消息代理（broker）再由消息代理广播给当前连接的客户端
        registry.enableSimpleBroker("/topic","/queue");
        //3、给指定用户发送一对一的主题前缀    默认为/user，可修改
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * 添加websocket连接管理
     * @param registry
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(webSocketManager);
        WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);
    }
}
