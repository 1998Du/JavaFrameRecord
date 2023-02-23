package com.dwk.gateway.result;

import java.io.Serializable;

/**
 * 登录验证的返回值
 */
public class LoginResult implements Serializable {
    public LoginResult() {

    }

    public LoginResult(int loginCode, String loginMessage, String token) {
        this.loginCode = loginCode;
        this.loginMessage = loginMessage;
        this.token = token;
    }

    private int loginCode;

    private String loginMessage;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(int loginCode) {
        this.loginCode = loginCode;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }
}
