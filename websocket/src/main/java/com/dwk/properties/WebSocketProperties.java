package com.dwk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "websocket")
public class WebSocketProperties {

    /**websocket前缀地址*/
    private List<String> websocketPrefix;
    /**websocket连接端点*/
    private List<String> endPoint;

}
