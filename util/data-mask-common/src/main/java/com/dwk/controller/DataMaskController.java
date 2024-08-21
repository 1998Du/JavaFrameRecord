package com.dwk.controller;

import com.dwk.result.CommonResult;
import com.dwk.service.DataMaskService;
import com.dwk.util.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author duweikun
 * @date 2023/7/19  15:22
 * @description
 * 存量数据加解密控制器
 */
@RestController
public class DataMaskController {

    @Autowired
    private DataMaskService dataMaskService;

    /**
     * 存量数据加密
     * @return
     */
    @PostMapping("/dataMask/run")
    public CommonResult run(){
        return CommonResult.success(dataMaskService.run());
    }

    /**
     * 解密
     * @param
     * @return
     */
    @PostMapping("/dataMask/decode")
    public CommonResult decode(@RequestBody Map<String,String> map){
        return CommonResult.success(AesUtil.decode(map.get("text")));
    }

    /**
     * 加密
     * @param
     * @return
     */
    @PostMapping("/dataMask/encode")
    public CommonResult encode(@RequestBody Map<String,String> map){
        return CommonResult.success(AesUtil.encode(map.get("text")));
    }

}
