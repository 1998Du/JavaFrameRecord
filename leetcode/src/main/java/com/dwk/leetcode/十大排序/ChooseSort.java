package com.dwk.leetcode.十大排序;

import java.util.Arrays;

/**
 * @author duweikun
 * @date 2023/7/13  17:09
 * @description
 *  选择排序
 *      最没用但是最简单的排序
 *          每次找到最小的或者最大的进行交换
 */
public class ChooseSort {

    public static void main(String[] args) {
        int[] nums = {12,3,5,15,34,6,2356,23,5,346,245,756,8,678,51,35,1,6,467,6,134,6245,7,245,64,7,7,245,7356,8};
        Arrays.stream(run(nums)).forEach(System.out::println);
    }

    public static int[] run(int[] nums){
        // 每次找到极值
        for(int i = 0; i<nums.length; i++){
            // 表示下标位置
            int index = i;
            for(int j = i+1; j<nums.length; j++){
                index = nums[j] < nums[index] ?  j : index;
            }
            // 交换
            int temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;
        }
        return nums;
    }

}
