package com.dwk.weixin.info;

import com.alibaba.fastjson.JSON;

public interface User {

    /**获取用户的详细信息*/
    JSON getUserInfo(String openId);

    /**用户关注*/
    String subscribe(String fans, String timestamp,String nonce);

    /**用户取消关注*/
    String unsubscribe(String fans);

}
