package com.dwk.request;


import com.dwk.common.CommonRest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("getRequest")
public class GetRequest extends CommonRest<Object> {

    @Override
    public String getUrl() {
        return properties.getGetProperties().getUrl();
    }

    @Override
    public HttpHeaders buildHead() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("test","test");
        return httpHeaders;
    }

    @Override
    public Object buildBody() {
        Map body = new HashMap();
        body.put("test","test");
        return body;
    }

    @Override
    public Object request(String url, HttpHeaders requestHead, Object requestBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(requestBody, requestHead);
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    @Override
    public Object resultHandle(Object result) {
        //JSONObject jsonObject = JSONObject.parseObject(result.toString());
        System.out.println("html文件返回，就不处理了！！！");
        return result.toString();
    }
}
