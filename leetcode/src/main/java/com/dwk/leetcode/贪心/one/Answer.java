package com.dwk.leetcode.贪心.one;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author duweikun
 * @date 2023/6/8  16:15
 * @description  最大回文字符串
 */
public class Answer {

    public static void main(String[] args) {
        while (true) {
            String scan = scan();
            System.out.println(longestPalindrome(scan));
            System.out.println(longestPalindrome1(scan));
        }
    }

    public static String scan(){
        return new Scanner(System.in).next();
    }

    public static int longestPalindrome(String s) {
        Map<Character,Integer> map = new HashMap<>();
        int result = 0;
        char[] charArray = s.toCharArray();
        for (char item : charArray){
            if (!map.containsKey(item)){
                map.put(item,1);
            }else{
                map.put(item,map.get(item)+1);
            }
        }
        for (int i : map.values()){
            result += i / 2 * 2;
            if (i % 2 == 1 && result % 2 == 0){
                result++;
            }
        }
        return result;
    }

    /**
     * 官方题解  思路差不多，只不过把map换成了int数组
     */
    public static int longestPalindrome1(String s) {
        int[] count = new int[128];
        int length = s.length();
        for (int i = 0; i < length; ++i) {
            char c = s.charAt(i);
            count[c]++;
        }

        int ans = 0;
        for (int v: count) {
            ans += v / 2 * 2;
            if (v % 2 == 1 && ans % 2 == 0) {
                ans++;
            }
        }
        return ans;
    }

}
