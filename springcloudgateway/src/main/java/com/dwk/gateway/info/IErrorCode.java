package com.dwk.gateway.info;

/**
 * 常用API返回对象接口
 */
public interface IErrorCode {
    /**
     * 返回码
     */
    String getCode();

    /**
     * 返回信息
     */
    String getMessage();
}
