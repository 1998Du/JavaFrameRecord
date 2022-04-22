package com.dwk.redis.result;

/**
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/4 9:05
 */
public class NbException extends RuntimeException{

    private final String errorMessage;
    private final int httpCode;

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public NbException(String errorMessage, int httpCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpCode = httpCode;
    }

    public NbException(Throwable cause, String errorMessage, int httpCode) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
        this.httpCode = httpCode;
    }

}
