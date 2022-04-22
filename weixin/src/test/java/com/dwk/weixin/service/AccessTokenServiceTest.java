package com.dwk.weixin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccessTokenServiceTest {

    @Autowired
    private AccessTokenService accessTokenService;

    @Test
    public void getAccessToken() {
        accessTokenService.getAccessToken();
    }
}