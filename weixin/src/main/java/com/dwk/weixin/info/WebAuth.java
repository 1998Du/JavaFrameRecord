package com.dwk.weixin.info;

public interface WebAuth {

    /**网页授权获取用户信息*/
    Object getOpenId(String code);

}
