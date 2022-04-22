package com.dwk.weixin.result;

/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/4 9:05
 */
public class HttpException extends RuntimeException{

    private final String errorMessage;
    private final int httpCode;

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public HttpException(String errorMessage, int httpCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpCode = httpCode;
    }

    public HttpException(Throwable cause, String errorMessage, int httpCode) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
        this.httpCode = httpCode;
    }

}
