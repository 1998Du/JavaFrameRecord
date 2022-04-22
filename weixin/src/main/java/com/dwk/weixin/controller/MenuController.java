package com.dwk.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.dwk.weixin.result.Result;
import com.dwk.weixin.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Description:
 *          自定义菜单
 * @author: mushi
 * @Date: 2021/2/21 15:03
 */
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**创建菜单*/
    @PostMapping("/createMenu")
    @ResponseBody
    public Result createMenu(@RequestBody String menuSetting) {
        Object jsonObject = JSONObject.parse(menuSetting);
        return menuService.createMenu(jsonObject);
    }

    /**查询菜单*/
    @GetMapping("/findMenu")
    @ResponseBody
    public String findMenu(){
        return String.valueOf(menuService.findMenu());
    }

    /**删除菜单*/
    @GetMapping("/deleteMenu")
    @ResponseBody
    public String deleteMenu(){
        return String.valueOf(menuService.deleteMenu());
    }

}
