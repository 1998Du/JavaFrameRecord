package com.dwk.weixin.bean;

import org.springframework.stereotype.Component;

/**
 *
 * Description:
 *      变量名接收微信服务器消息时要使用到，不可修改
 * @author: mushi
 * @Date: 2021/3/10 16:11
 */
@Component
public class AccessTokenBean {

    /**凭证*/
    private String access_token;
    /**有效时间*/
    private Integer expires_in;
    /**错误代码*/
    private Integer errcode;
    /**错误信息*/
    private String errmsg;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public boolean success(){
        if (this.getAccess_token()!=null){
            return true;
        }else if(this.getErrmsg()!=null){
            return false;
        }else{
            throw new RuntimeException("未接收到微信服务器任何消息！");
        }
    }
}
