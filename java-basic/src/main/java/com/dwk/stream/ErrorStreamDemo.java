package com.dwk.stream;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * stream并不基于内存操作，不会直接修改原对象内的数据
 */
public class ErrorStreamDemo {

    public static void main(String[] args) {
        test();
    }

    public static void test(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        filter(list);
        System.out.println("主："+JSONArray.toJSONString(list));
    }

    private static final String TARGET = "1,2,3,4,5";

    public static void filter(List<String> list){
        for (int i = 0; i<list.size();i++){
            boolean contains = TARGET.contains(list.get(i));
            if (contains){
                list.remove(list.get(i));
            }
        }
        list = list.stream().filter(item -> !TARGET.contains(item)).collect(Collectors.toList());
        System.out.println("filter:"+list);
    }

}

class Filter{

    private static final String TARGET = "1,2,3,4,5";

    public void filter(List<String> list){
        list = list.stream().filter(item -> TARGET.contains(item)).collect(Collectors.toList());
    }

}
