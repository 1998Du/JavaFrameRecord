package com.dwk.gateway.constant;

/**
 * 通用常量信息
 */
public class Constants {

    /**websocket消息代理前缀，表示广播的映射前需要加上/topic，表示点对点的映射前需要加上/queue*/
    public static final String[] MESSAGE_BROKER_PREFIX = {"/topic","/queue"};
    public static final String USERNAME = "username";
    public static final String USERID = "user_id";
    public static final String TOKEN = "token";
    public static final String SESSION = "session";
    public static final String USER = "user";
    public static String CURRENT_ID = "current_id";
    public static String CURRENT_USERNAME = "current_username";
    public static String UTF8 = "utf-8";

}
