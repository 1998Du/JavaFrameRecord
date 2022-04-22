//package com.dwk.weixin.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.dwk.weixin.feign.AuthFeign;
//import com.dwk.weixin.result.LoginResult;
//import com.dwk.weixin.service.WebAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//
///**
// *
// * Description:
// *      用户通过外部链接登录云GIS系统，要使用微信的网页授权获取用户基本信息接口
// * @author: mushi
// * @Date: 2021/1/19 13:50
// */
//@RestController
//public class LoginThirdSysController {
//
//    @Autowired
//    private WebAuthService webAuthService;
//    @Autowired
//    private AuthFeign authFeign;
//
//    /***
//     *
//     * 获取url中的code
//     * 通过code获取用户的OpenId
//     * 判断获取到的OpenId是否在用户表中存在
//     * 如果存在则使用该OpenId对应的用户的账户密码进行登录
//     * 如果不存在则跳转登录页
//     * 用户在登录页成功登录后将该用户的OpenId添加到用户表
//     */
//
//    /**
//     * 服务号才可以使用，订阅号可以通过自动回复链接的方式使用微信id登录第三方系统
//     * 网页授权获取相关数据,微信中的外链指向此处*/
//    @GetMapping("/loginByMenu")
//    public LoginResult loginByMenu(String code) {
//
//        if (code!=null){
//            //获取用户OpenId
//            String data = webAuthService.getOpenId(code).toString();
//            System.out.println("网页授权获取的数据:"+data);
//            JSONObject jsonObject = JSONObject.parseObject(data);
//            String openId = jsonObject.get("openid").toString();
//            System.out.println("用户的openId："+openId);
//
//            //调用authcenter.server中AuthController的login/{openId}进行用户验证
//            return authFeign.login(openId);
//            //return openId;
//        }else{
//            return null;
//        }
//
//    }
//
//    /**通过链接登录（链接中携带用户的微信id）*/
//    @GetMapping("/loginByUrl")
//    public LoginResult loginByUrl(String openId){
//        //通过用户的微信id登录
//        return authFeign.login(openId);
//    }
//
//
//
//}
