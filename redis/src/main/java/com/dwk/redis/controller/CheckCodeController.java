package com.dwk.redis.controller;

import com.dwk.redis.bean.CheckCodeBean;
import com.dwk.redis.result.Result;
import com.dwk.redis.service.CheckCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/12 10:11
 */
@RestController
public class CheckCodeController {

    @Autowired
    private CheckCodeServiceImpl checkCodeService;

    /**判断用户是否存在*/
    @GetMapping("/userIsExit")
    public Result userIsExit(String userName){
        //根据用户名判断用户是否存在

        return Result.success();
    }

    /**注册*/
    @GetMapping("/regist")
    public Result regist(){
        //从微信公众号注册，调用微信公众号服务，将用户名和openId保存(先保存到数据库再从数据库读取到缓存，保证数据一致)

        return Result.success();
    }

    /**验证登录*/
    @PostMapping("/login")
    public Result login(@RequestBody String openId){
        String checkCode = checkCodeService.creatCheckCode(6);
        //验证码缓存到redis，有效时间60秒
        CheckCodeBean codeBean = checkCodeService.cacheCode(openId, checkCode);
        //调用微信公众号服务，将验证码发送给指定用户

        //验证码key
        return Result.success("验证码key:"+codeBean.getKey());

    }

    /**检查用户输入的验证码*/
    @PostMapping("/checkCode")
    public Result checkCode(@RequestBody CheckCodeBean codeBean){
        return checkCodeService.checkCheckCode(codeBean);
    }

}

