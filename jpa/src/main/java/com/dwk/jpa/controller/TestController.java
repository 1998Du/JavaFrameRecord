package com.dwk.jpa.controller;

import com.dwk.jpa.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/16 15:41
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/")
    public void test(){
        testService.test();
    }

}
