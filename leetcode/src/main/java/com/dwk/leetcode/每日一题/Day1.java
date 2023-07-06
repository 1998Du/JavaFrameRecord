package com.dwk.leetcode.每日一题;

import java.util.Arrays;

/**
 * @author duweikun
 * @date 2023/7/5  10:17
 * @description 1707. 与数组中元素的最大异或值 异或 ^  相同为0不同为1
 */
public class Day1 {

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        int[] nums = {0,1,2,3,4};
        int[][] queries = {{3,1},{1,3},{5,6}};
        Arrays.stream(day1.maximizeXor(nums, queries)).forEach(System.out::println);
    }

    /**
     * 暴力解法  耗时严重
     * @param nums
     * @param queries
     * @return
     */
    public int[] maximizeXor(int[] nums, int[][] queries) {
        int[] result = new int[queries.length];
        for (int i = 0; i<queries.length; i++){
            int x = queries[i][0];
            int m = queries[i][1];
            int[] array = Arrays.stream(nums).filter(item -> item <= m).toArray();
            if (array.length == 0 || array == null){
                result[i] = -1;
            }else{
                result[i] = Arrays.stream(array).map(item -> item ^ x).max().getAsInt();
            }
        }
        return result;
    }

    /**
     * 官方解法：离线询问+字段树
     */

}
