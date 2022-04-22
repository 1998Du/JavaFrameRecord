package com.dwk.design.result;

/**
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/4 9:03
 */
public enum ErrorType {

    BUSINESS_ERROR("500", "系统异常"),
    SYSTEM_ERROR("501", "系统异常"),
    UNPERMISSION("402", "未授权"),
    UNLOGIN("401", "未登陆"),
    SYSTEM_BUSY("000001", "系统繁忙,请稍候再试"),
    SYSTEM_NO_PERMISSION("000002", "无权限"),
    GATEWAY_NOT_FOUND_SERVICE("404", "服务未找到"),
    GATEWAY_ERROR("502", "网关异常"),
    GATEWAY_CONNECT_TIME_OUT("503", "网关超时"),
    ARGUMENT_NOT_VALID("020000", "请求参数校验不通过"),
    FILE_NOT_EXIT("10001","文件不存在"),
    UPLOAD_FILE_SIZE_LIMIT("020001", "上传文件大小超过限制");


    private String code;
    private String message;

    private ErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
