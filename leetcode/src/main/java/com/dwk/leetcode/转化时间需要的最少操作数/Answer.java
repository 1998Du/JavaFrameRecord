package com.dwk.leetcode.转化时间需要的最少操作数;

public class Answer {
    public static void main(String[] args) {
        String current = "02:30";
        String correct = "04:35";
        System.out.println("逻辑计算:"+run(current, correct));
        System.out.println("贪心算法:"+greed(current, correct));
    }


    /**
     * 逻辑计算
     * @param current
     * @param correct
     * @return
     */
    public static int run(String current,String correct){
        int returnInt = 0;
        // 计算两者相差多少分钟
        String[] currentSplit = current.split(":");
        String[] correctSplit = correct.split(":");
        int now = Integer.parseInt(currentSplit[0]) * 60 + Integer.parseInt(currentSplit[1]);
        int target = Integer.parseInt(correctSplit[0]) * 60 + Integer.parseInt(correctSplit[1]);
        int diff = target - now;
        // 和5求余
        int need1 = diff % 5;
        // (diff-need1) 一定是5的倍数,判断多少个小时
        int need60 = (diff-need1)/60;
        // 和60求余,一定是5的倍数
        int j = (diff-need1) % 60;
        // 计算是 5 的几倍
        int K = j / 5;
        // 15/5 = 3  求与3的商就能得出需要加多少个15
        int need15 = K / 3;
        // 和3求余就能得出需要多少个5
        int need5 = K % 3;
        returnInt = need1 + need5 + need15 + need60;
        return returnInt;
    }

    /**
     * 贪心算法
     */
    public static int greed(String current,String correct){
        int returnInt = 0;
        // 计算两者相差多少分钟
        String[] currentSplit = current.split(":");
        String[] correctSplit = correct.split(":");
        int diff = Integer.parseInt(correctSplit[0]) * 60 + Integer.parseInt(correctSplit[1]) - Integer.parseInt(currentSplit[0]) * 60 - Integer.parseInt(currentSplit[1]);
        int[] nums = {60,15,5,1};
        for (int t : nums){
            returnInt += diff/t;
            diff %= t;
        }
        return returnInt;
    }

}
