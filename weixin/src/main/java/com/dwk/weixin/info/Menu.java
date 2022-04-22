package com.dwk.weixin.info;

import com.dwk.weixin.result.Result;


public interface Menu {

    /**创建菜单*/
    Result createMenu(Object menus);

    /**查询菜单*/
    Object findMenu();

    /**删除菜单*/
    Object deleteMenu();

}
