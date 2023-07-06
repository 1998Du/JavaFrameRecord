package com.dwk.leetcode.每日一题.两数之和;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 思路：遍历数组中数据，挨个和目标值比较，若大于目标值则不处理
 * 若小于目标值，则用目标值减去该值所得的数为key到map中查询是否存在，若存在则取其下标
 */
public class Answer {

    private static Map<Integer, Integer> map = new HashMap<>();

    private static int[] result;

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 13;
        //方式1
        result = twoSumByMap(nums, target);
        System.out.println(Arrays.toString(result));
        //方式2
        result = twoSumByLoop(nums, target);
        System.out.println(Arrays.toString(result));
    }

    //1、使用map
    public static int[] twoSumByMap(int[] nums, int target) {
        int[] returnArray = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (target > nums[i] && map.containsKey(target - nums[i])) {
                returnArray[0] = map.get(target - nums[i]);
                returnArray[1] = i;
            }
            map.put(nums[i], i);
        }
        return returnArray;
    }

    //2、暴力破解
    public static int[] twoSumByLoop(int[] nums, int target) {
        int[] returnArray = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int other = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == other) {
                    returnArray[0] = i;
                    returnArray[1] = j;
                }
            }
        }
        return returnArray;
    }

}
