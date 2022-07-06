package com.dwk.request;

import com.alibaba.fastjson.JSONObject;
import com.dwk.common.CommonRest;
import com.dwk.entity.PostEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("postRequest")
public class PostRequest extends CommonRest<PostEntity> {
    @Override
    public String getUrl() {
        return properties.getPostProperties().getUrl();
    }

    @Override
    public HttpHeaders buildHead() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept","*/*");
        httpHeaders.set("Accept-Encoding","UTF-8");
        httpHeaders.set("Accept-Language","zh-CN%2Czh%3Bq%3D0.9%2Cen%3Bq%3D0.8%2Cen-GB%3Bq%3D0.7%2Cen-US%3Bq%3D0.6");
        httpHeaders.set("Connection","keep-alive");
        httpHeaders.set("Content-Length","154");
        httpHeaders.set("Content-Type","application/json");
        httpHeaders.set("Cookie","pac_uid%3D0_85c1f87505b2d%3B%20iip%3D0%3B%20pgv_pvid%3D1464197140%3B%20qm_device_id%3DfvR60FEWcdEvr61knnSjrRW6hP%2Bxz6Bs64l7kzWehevzIUutGbfqurG7yIPq7LQA%3B%20CCSHOW%3D000001%3B%20webp%3D1%3B%20ptui_loginuin%3D1379134481%3B%20RK%3DX5N8xx3lQA%3B%20ptcz%3D86afcd3b89735f9d619dc1b1dce71b81f4bd7f88aa79a018984f25f6ca68f0fa%3B%20pt_sms_phone%3D139******85%3B%20edition%3Dmail.qq.com%3B%20qm_logintype%3Dqq%3B%20uin%3Do1379134481%3B%20skey%3D%40WzvvsDaJ1%3B%20p_uin%3Do1379134481%3B%20pt4_token%3D2M--lXXvzNVbTygyPI8p2*dnJ8493lOTQRpL52r2cu0_%3B%20p_skey%3DlzEy911CMsRcjt5ctBmFCDRNuhHHqlNljmdFbIGz4lE_%3B%20xm_envid%3D456_bV9K8kFzf0RkmRo2vU%2BhnY286Btw7G%2Fk0pHXdONn6x1Iqi0F9%2BKh1VBPQ4j0kZa3bqnUSNlOwPC0jrS4RYcUrP71iOEO2cWbqIS7P3wdyrI%2BpEzRqkve4Ibv7bmY3oTzKOfN0TcS2%2FTVntG6egmv26FEDJMVB8g%3D%3B%20qm_username%3D1379134481%3B%20qm_domain%3Dhttps%3A%2F%2Fmail.qq.com%3B%20ssl_edition%3Dsail.qq.com%3B%20username%3D1379134481%261379134481%3B%20sid%3D1379134481%2621159a9bb03106df447fbea637c3c1a5%2CqbHpFeTkxMUNNc1JjanQ1Y3RCbUZDRFJOdWhISHFsTmxqbWRGYklHejRsRV8.%3B%20xm_uin%3D13102662404008977%3B%20xm_sid%3DzREweozsNzEuM1pmAFIzagAA%3B%20xm_muti_sid%3D13102662404008977%26zREweozsNzEuM1pmAFIzagAA%3B%20xm_skey%3D13102662404008977%2657c041cc2393b0a61815180622946fd2%3B%20xm_data_ticket%3D13102662404008977%26CAESIC-X396XmVz6bpX6wyO9A3x9749VgAoeE-sEFr7rwsX7%3B%20tinfo%3D1654762576.0000**%3B%20new_mail_num%3D1379134481%2610");
        httpHeaders.set("DNT","1");
        httpHeaders.set("Host","wx.mail.qq.com");
        httpHeaders.set("Origin","https%3A%2F%2Fwx.mail.qq.com");
        httpHeaders.set("Referer","https%3A%2F%2Fwx.mail.qq.com%2Flist%2Freadtemplate%3Fname%3Dajax_proxy.html");
        httpHeaders.set("sec-ch-ua","%22%20Not%20A%3BBrand%22%3Bv%3D%2299%22%2C%20%22Chromium%22%3Bv%3D%22102%22%2C%20%22Microsoft%20Edge%22%3Bv%3D%22102%22");
        httpHeaders.set("sec-ch-ua-mobile","%3F0");
        httpHeaders.set("sec-ch-ua-platform","%22windows%22");
        httpHeaders.set("Sec-Fetch-Dest","empty");
        httpHeaders.set("Sec-Fetch-Mode","cors");
        httpHeaders.set("Sec-Fetch-Site","same-origin");
        httpHeaders.set("User-Agent","Mozilla%2F5.0%20(Windows%20NT%2010.0%3B%20Win64%3B%20x64)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F102.0.5005.63%20Safari%2F537.36%20Edg%2F102.0.1245.33");
        return httpHeaders;
    }

    @Override
    public Object buildBody() {
        Map returnMap = new HashMap();
        returnMap.put("enc","1");
        returnMap.put("func","1");
        returnMap.put("sid","QVAf5AikT3zdWwlr");
        returnMap.put("cookie_sid","1379134481&21159a9bb03106df447fbea637c3c1a5,qbHpFeTkxMUNNc1JjanQ1Y3RCbUZDRFJOdWhISHFsTmxqbWRGYklHejRsRV8.");
        return returnMap;
    }

    @Override
    public Object request(String url, HttpHeaders requestHead, Object requestBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody, requestHead);
        ResponseEntity<PostEntity> result = restTemplate.postForEntity(url, httpEntity, PostEntity.class);
        return result.getBody();
    }

    @Override
    public PostEntity resultHandle(Object result) {
        PostEntity postEntity = JSONObject.parseObject(JSONObject.toJSONString(result), PostEntity.class);
        return postEntity;
    }
}
