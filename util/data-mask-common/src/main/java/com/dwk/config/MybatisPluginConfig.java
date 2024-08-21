package com.dwk.config;

import com.dwk.plugin.DataMaskParamPlugin;
import com.dwk.plugin.DataMaskResultPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author duweikun
 * @date 2023/7/19  9:30
 * @description  插件配置
 */
@Configuration
public class MybatisPluginConfig {

    @Bean
    public DataMaskParamPlugin dataMaskQueryPlugin(){return new DataMaskParamPlugin();}

    @Bean
    public DataMaskResultPlugin dataMaskUpdatePlugin(){return new DataMaskResultPlugin();}

}
