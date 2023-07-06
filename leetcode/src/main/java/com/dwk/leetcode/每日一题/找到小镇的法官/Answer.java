package com.dwk.leetcode.每日一题.找到小镇的法官;

import java.util.ArrayList;
import java.util.List;

public class Answer {

    public static void main(String[] args) {
        int n = 4;
        int[][] trust = {{1,3},{1,4},{2,3},{2,4},{4,3}};
        long start1 = System.currentTimeMillis();
        int judge1 = findJudge(n, trust);
        long end1 = System.currentTimeMillis();
        System.out.println("执行时间:" + (end1-start1) + "结果" + judge1);

        long start2 = System.currentTimeMillis();
        int judge2 = findJudge2(n, trust);
        long end2 = System.currentTimeMillis();
        System.out.println("执行时间:" + (end2-start2) + "结果" + judge2);

        long start3 = System.currentTimeMillis();
        int judge3 = findJudge3(n, trust);
        long end3 = System.currentTimeMillis();
        System.out.println("执行时间:" + (end3-start3) + "结果" + judge3);
    }

    /**
     * 逻辑计算
     */
    public static int findJudge(int n, int[][] trust){
        int returnInt = -1;
        int count = 0;
        List<Integer> allPerson = new ArrayList<>();
        for (int i = 1; i <= n;i++){
            allPerson.add(i);
        }
        for (int[] t : trust){
            //移除肯定不是法官的
            allPerson.remove((Object)t[0]);
        }
        if (allPerson.size() == 1){
            int judge = allPerson.get(0);
            //判断法官是否被所有人都信任
            for (int[] t : trust){
                if (t[1] == judge){
                    count += 1;
                }
            }
            if (count == n-1){
                returnInt = judge;
            }
        }
        return returnInt;
    }


    /**
     * 用一个数组存储每个人信任次数和被信任次数,最后信任次数为0且被信任次数为n-1的就是法官
     */
    public static int findJudge2(int n, int[][] trust){
        int returnInt = -1;
        // 人\信任次数\被信任次数
        int[][] count = new int[n][3];
        // 初始化所有人
        for (int i = 1; i<=n; i++){
            count[i-1] = new int[]{i, 0, 0};
        }
        // 统计每个人信任次数和被信任次数
        for (int[] t : trust){
            count[t[0]-1][0] = t[0];
            count[t[0]-1][1] += 1;
            count[t[1]-1][2] += 1;
        }
        // 查找满足信任次数为0,被信任次数为n-1的人
        for(int[] person : count){
            if (person[1] == 0 && person[2] == n-1){
                returnInt = person[0];
            }
        }
        return returnInt;
    }

    /**
     * 官方题解
     */
    public static int findJudge3(int n, int[][] trust) {
        int[] inDegrees = new int[n + 1];
        int[] outDegrees = new int[n + 1];
        for (int[] edge : trust) {
            int x = edge[0], y = edge[1];
            ++inDegrees[y];
            ++outDegrees[x];
        }
        for (int i = 1; i <= n; ++i) {
            if (inDegrees[i] == n - 1 && outDegrees[i] == 0) {
                return i;
            }
        }
        return -1;
    }

}
