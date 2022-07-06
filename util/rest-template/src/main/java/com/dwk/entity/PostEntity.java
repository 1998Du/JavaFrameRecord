package com.dwk.entity;

import lombok.Data;

@Data
public class PostEntity {

    private Head head;
    private Param param;
    private Body body;
}

@Data
class Head{
    private int ret;
    private String cgi;
    private String time;
    private String msg;
}

@Data
class Param{
    private String protocol;
    private String hostname;
    private String sid;
}
@Data
class Body{
    private String xm_uin;
    private String xm_sid;
    private String xm_sky;
}
