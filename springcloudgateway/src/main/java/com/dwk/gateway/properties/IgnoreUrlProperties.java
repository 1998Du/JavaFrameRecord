package com.dwk.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "gateway")
public class IgnoreUrlProperties {

    /**放行路径*/
    List<String> ignoreUrl;

}

