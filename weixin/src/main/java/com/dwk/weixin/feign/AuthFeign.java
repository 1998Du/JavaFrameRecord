package com.dwk.weixin.feign;


import com.dwk.weixin.result.LoginResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 将openId发送到gis系统登录验证的/login/{openId}接口
 * @author mushi
 * */
//@Component
//@FeignClient(name = "")
public interface AuthFeign {

    /**调用login服务登录接口*/

//    @GetMapping(path = "/login/{openId}")
//    LoginResult login(@PathVariable(value = "openId") @RequestBody String openId);


}
