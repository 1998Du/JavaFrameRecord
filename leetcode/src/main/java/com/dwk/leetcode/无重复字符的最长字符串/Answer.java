package com.dwk.leetcode.无重复字符的最长字符串;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Answer {

    private static final String str = "21736481281285";

    public static void main(String[] args) {
        int official = official(str);
        System.out.println("官方方法:" + official);
        int i = method1(str);
        System.out.println("方法1："+i);
        int i1 = method2(str);
        System.out.println("方法2："+i1);
    }

    public static int official(String str){
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = str.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(str.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(str.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(str.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }


    /**暴力递归*/
    public static int method1(String str){
        int returnInt = 0;
        char[] chars = str.toCharArray();
        List<Character> list = new ArrayList<>();
        for (int i = 0;i<chars.length;i++){
            for(int j = i;j<chars.length;j++) {
                if (list.contains(chars[j])) {
                    if (returnInt != 0) {
                        returnInt = list.size() > returnInt ? list.size() : returnInt;
                    } else {
                        returnInt = list.size();
                    }
                    list.clear();
                } else {
                    list.add(chars[j]);
                }
            }
        }
        return returnInt;
    }

    /**排除重复段*/
    public static int method2(String str){
        int returnInt = 0;
        char[] chars = str.toCharArray();
        List<Character> list = new ArrayList<>();
        for (int i = 0;i<chars.length;i++){
            if (list.contains(chars[i])){
                //移除前置重复段
                int index = list.indexOf(chars[i]);
                i = index + 1;
                for (;index >= 0;index--){
                    list.remove(index);
                }
            }else{
                list.add(chars[i]);
                returnInt = list.size();
            }
        }
        return returnInt;
    }

}