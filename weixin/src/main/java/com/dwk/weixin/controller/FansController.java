package com.dwk.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.dwk.weixin.service.AccessTokenService;
import com.dwk.weixin.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * Description:
 *          获取所有粉丝openId
 * @author: mushi
 * @Date: 2021/1/22 14:36
 */
@RestController
public class FansController {

    /**接口地址前缀*/
    private String urlstr = "https://api.weixin.qq.com/cgi-bin/user/get";
    /**用户总数*/
    private Integer total;
    /**获取粉丝数*/
    private Integer count;
    /**获取到粉丝的openId*/
    private List<String> fans;

    @Autowired
    private AccessTokenService accessTokenService;


    /**获取微信公众号所有粉丝的id*/
    @GetMapping("/getAllFans")
    public List getAllFans(){
        String accessToken = accessTokenService.getAccessTokenFromRedis();
        String nextOpenid = "";
        String realUrl = urlstr+"?access_token="+accessToken+"&next_openid="+nextOpenid;
        String result = UrlUtil.requestUrl(realUrl).toString();

        JSONObject jsonObject = JSONObject.parseObject(result);
        total = (Integer) jsonObject.get("total");
        count = (Integer) jsonObject.get("count");
        JSONObject data = (JSONObject) jsonObject.get("data");
        fans = (List<String>) data.get("openid");
        System.out.println("总用户数："+total+"\n获取粉丝数："+count+"\n"+fans);
        return fans;
    }


}
