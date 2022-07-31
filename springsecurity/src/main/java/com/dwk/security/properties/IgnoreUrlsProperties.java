package com.dwk.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SpringSecurity白名单资源路径配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "security")
public class IgnoreUrlsProperties {

    /**白名单资源路径*/
    private List<String> ignoreUrls = new ArrayList<>();

}