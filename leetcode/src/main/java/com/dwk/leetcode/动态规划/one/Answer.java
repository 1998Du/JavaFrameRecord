package com.dwk.leetcode.动态规划.one;

import java.util.HashMap;
import java.util.Scanner;

/**
 * 思路 ： 每次只能+1或者+2   f(n) = f(n-1) + f(n-2)
 * 例如 输入3   要么先加到2再加1  要么先加到1再加2   那么要加到3的方法为  加到2的方法 + 加到1的方法
 */
public class Answer {

    private static HashMap<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        //System.out.println(climbStairs(scan()));
        System.out.println(climbStairs2(scan()));
    }

    public static int scan(){
        return new Scanner(System.in).nextInt();
    }

    // 由顶及下计算
    public static int climbStairs(int n){
        // 边界
        if (n == 1){
            return 1;
        }
        if (n == 2){
            return 2;
        }
        // 去除重复计算
        if (!map.containsKey(n)) {
            // 最优子结构 状态转移方程
            map.put(n, climbStairs(n - 1) + climbStairs(n - 2));
        }
        return map.get(n);
    }

    // 由下及顶计算
    public static int climbStairs2(int n){
        // 边界
        if (n == 1){
            return 1;
        }
        if (n == 2){
            return 2;
        }
        for(int i = 3;i <= n;i++){
            // 由下及顶计算
            if(!map.containsKey(i)) {
                map.put(i, climbStairs2(i - 1) + climbStairs2(i - 2));
            }
        }
        return map.get(n);
    }

}

