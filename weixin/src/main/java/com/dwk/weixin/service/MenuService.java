package com.dwk.weixin.service;

import com.dwk.weixin.info.Menu;
import com.dwk.weixin.result.ErrorType;
import com.dwk.weixin.result.Result;
import com.dwk.weixin.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 *
 * Description:
 *          定义菜单
 * @author: mushi
 * @Date: 2021/2/21 14:47
 */
@Service
public class MenuService implements Menu {

    @Autowired
    private AccessTokenService accessTokenService;

    /**创建菜单接口地址前缀*/
    private static final String CREATE_PREFIX = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    /**查找菜单接口地址前缀*/
    private static final String FIND_PREFIX = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=";

    /**删除菜单接口地址前缀*/
    private static final String DELETE_PEFIX = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";

    /**
     * 创建菜单
     * 参数格式参考：https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html
     * */
    @Override
    public Result createMenu(Object menus) {
        System.out.println("菜单设置:"+menus);
        String url = CREATE_PREFIX +accessTokenService.getAccessTokenFromRedis();
        try {
            return UrlUtil.requestUrlWithJson(menus,url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail(ErrorType.MENU_CREATE_FAIL);
    }

    /**查询菜单*/
    @Override
    public Object findMenu(){
        String url = FIND_PREFIX +accessTokenService.getAccessTokenFromRedis();
        return UrlUtil.requestUrl(url);
    }

    /**删除菜单*/
    @Override
    public Object deleteMenu(){
        String url = DELETE_PEFIX +accessTokenService.getAccessTokenFromRedis();
        return UrlUtil.requestUrl(url);
    }


}
