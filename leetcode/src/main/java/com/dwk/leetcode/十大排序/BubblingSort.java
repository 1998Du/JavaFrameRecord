package com.dwk.leetcode.十大排序;

import java.util.Arrays;

/**
 * 冒泡排序
 *   每次找到 最大的 或者 最小的
 */
public class BubblingSort {

    public static void main(String[] args) {
        int[] nums = {1,3,4,2,5,6,8,7,9,0};
        run(nums);
    }

    public static int[] run(int[] nums){
        int k;
        for (int i = 0; i<nums.length; i++){
            for (int j = i+1; j<nums.length; j++){
                if (nums[i] > nums[j]){
                    k = nums[i];
                    nums[i] = nums[j];
                    nums[j] = k;
                }
            }
            System.out.println("第" + (i+1) + "次排序 : " + Arrays.toString(nums));
        }
        return nums;
    }

}
