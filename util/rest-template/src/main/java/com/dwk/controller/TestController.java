package com.dwk.controller;

import com.dwk.common.CommonRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private Map<String, CommonRest> commonRestMap;

    @RequestMapping(value = "/getTest",method = RequestMethod.GET)
    public void testGet(){
        commonRestMap.get("getRequest").run();
    }

    @RequestMapping(value = "/postTest",method = RequestMethod.POST)
    public void testPost(){
        commonRestMap.get("postRequest").run();
    }

}
