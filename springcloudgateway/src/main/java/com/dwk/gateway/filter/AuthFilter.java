package com.dwk.gateway.filter;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dwk.gateway.constant.Constants;
import com.dwk.gateway.properties.IgnoreUrlProperties;
import com.dwk.gateway.result.ErrorType;
import com.dwk.gateway.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;

/**
 * http请求拦截
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private IgnoreUrlProperties ignoreUrlProperties;


    /**
     * 请求拦截器
     * redis中存储token和用户信息: {access_token_xxxxxxxx:"{"userId":"","loginName":""}"}
     * 请求头中token格式：token：xxxxx
     * 接收到请求后对token做一次校验
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //判断是否socket请求
        String url = exchange.getRequest().getURI().getPath();
        URI requestUrl = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        log.info("gateway filter => " + requestUrl);
        String scheme = requestUrl.getScheme();
        if (!ServerWebExchangeUtils.isAlreadyRouted(exchange) && ("ws".equals(scheme) || "wss".equals(scheme))){
            //放行交给WebSocketFilter处理
            return chain.filter(exchange);
        }
        return httpFilter(exchange,chain,url);
    }

    /**
     * http请求过滤器
     * @param exchange
     * @param chain
     * @param url
     * @return
     */
    public Mono<Void> httpFilter(ServerWebExchange exchange, GatewayFilterChain chain,String url){
        // 跳过不需要验证的路径
        if (ignoreUrlProperties.getIgnoreUrl().contains(url)) {
            return chain.filter(exchange);
        }
        // token判空
        String token = exchange.getRequest().getHeaders().getFirst(Constants.TOKEN);
        if (StrUtil.isBlank(token)) {
            return setUnauthorizedResponse(exchange, "token can't null or empty string");
        }
        //token验证
        String userStr = null;//ops.get(Constants.ACCESS_TOKEN + token);
        if (StrUtil.isBlank(userStr)) {
            return setUnauthorizedResponse(exchange, "token verify error");
        }
        JSONObject userTokenJson = JSONObject.parseObject(userStr);
        String userId = userTokenJson.getString("userId");
        if (StrUtil.isBlank(userId)) {
            return setUnauthorizedResponse(exchange, "token verify error");
        }
        ServerHttpRequest mutableReq = exchange.getRequest().mutate()
                .header(Constants.CURRENT_ID, userId)
                .header(Constants.CURRENT_USERNAME, userTokenJson.getString("loginName")).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();

        return chain.filter(mutableExchange);
    }


    /**
     * 未经授权的响应
     *
     * @param exchange
     * @param msg
     * @return
     */
    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] response = null;
        try {
            response = JSON.toJSONString(Result.fail(ErrorType.UNPERMISSION, msg)).getBytes(Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }

    /**
     * 设置拦截器的优先级
     * 必须在10000之后，否则获取 GATEWAY_REQUEST_URL_ATTR 时报错
     * @return
     */
    @Override
    public int getOrder() {
        return 10001;
    }

}
