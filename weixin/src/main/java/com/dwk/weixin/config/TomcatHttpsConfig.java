package com.dwk.weixin.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Description:
 *      访问http://localhost:8090跳转到---->https://localhost:700
 * @author: mushi
 * @Date: 2021/3/15 11:36
 */
@Configuration
public class TomcatHttpsConfig {


    /**拦截所有请求*/
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    /**配置http转https*/
    private Connector redirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        //监听http端口号
        connector.setScheme("http");
        connector.setPort(8090);
        //设置为false表示支持http和https两种请示，true表示只支持https
        connector.setSecure(false);
        //监听到http的端口号转向到https的端口号
        connector.setRedirectPort(700);
        return connector;
    }

}
