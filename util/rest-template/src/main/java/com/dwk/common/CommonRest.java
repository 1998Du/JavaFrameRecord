package com.dwk.common;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public abstract class CommonRest<T> {

    private static final Logger log = LoggerFactory.getLogger(CommonRest.class);

    @Autowired
    protected CommonProperties properties;

    /**请求入口*/
    public T run() {
        T data = null;
        String url = null;
        try {
            long start = System.currentTimeMillis();
            url = getUrl();
            Assert.notNull(url,"请求地址不能为空！");
            log.info("====>请求地址  " + url);

            HttpHeaders requestHead = buildHead();
            if (requestHead == null || ObjectUtils.isEmpty(requestHead)) {
                log.info("====>请求头为空");
            } else {
                log.info("====>组织请求头  " + new JSONObject(requestHead));
            }

            Object requestBody = buildBody();
            if (requestBody == null || ObjectUtils.isEmpty(requestBody)) {
                log.info("====>请求体为空");
            } else {
                log.info("====>组织请求体  " + requestBody.toString());
            }

            Object result = request(url,requestHead,requestBody);
            if (requestBody == null || ObjectUtils.isEmpty(requestBody)) {
                log.info("====>请求结果为空");
            } else {
                log.info("====>请求结果  " + result.toString());
            }

            data = resultHandle(result);
            if (data == null || ObjectUtils.isEmpty(data)) {
                log.info("====>请求结果为空");
            } else {
                log.info("====>处理请求结果  "+data.toString());
            }

            long end = System.currentTimeMillis();
            log.info("====>请求耗时  " + (end - start)/1000 + "秒");
        } catch (Exception e) {
            log.error("请求"+url+"失败");
            e.printStackTrace();
        }
        return data;
    }

    /**获取url并初始化请求参数*/
    public abstract String getUrl();

    /**构造请求头*/
    public abstract HttpHeaders buildHead();

    /**构造请求体*/
    public abstract Object buildBody();

    /**发送请求*/
    public abstract Object request(String url,HttpHeaders requestHead,Object requestBody);

    /**处理返回值*/
    public abstract T resultHandle(Object result);

}
