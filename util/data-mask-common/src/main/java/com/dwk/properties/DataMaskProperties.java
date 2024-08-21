package com.dwk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author duweikun
 * @date 2023/7/19  18:28
 * @description
 */
@Component
@ConfigurationProperties(prefix = "data-mask")
@Data
public class DataMaskProperties {

    Map<String, List<String>> config;

}
