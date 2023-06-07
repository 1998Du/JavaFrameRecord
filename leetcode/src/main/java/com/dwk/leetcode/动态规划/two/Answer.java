package com.dwk.leetcode.动态规划.two;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 思路 ： f(n) = f(n-1) + f(n-2)
 */
public class Answer {

    private static HashMap<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        System.out.println(fib(scan()));
    }

    public static int scan(){
        return new Scanner(System.in).nextInt();

    }

    /**动态规划*/
    public static int fib(int n){
        // 边界
        if (n == 0){
            return 0;
        }
        if (n == 1){
            return 1;
        }
        // 减少重复计算
        if (!map.containsKey(n)){
            // 最优子结构
            map.put(n,fib(n-1) + fib(n-2));
        }
        return map.get(n);
    }

    /**官方题解  动态规划+滚动数组*/
    public static int fib2(int n){
        if (n < 2){
            return n;
        }
        int p = 0, q = 0, r = 1;
        for (int i = 2; i <= n; ++i) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }

}

