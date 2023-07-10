package com.dwk.leetcode.十大排序;

import java.util.Arrays;

/**
 * 插入排序
 *   就像扑克排序，每次拿一个数和前面已经排好序的数比较
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] nums = {1,3,4,2,5,6,8,7,9,0};
        run(nums);
    }

    /**
     * 每次排序已经遍历过的数列都是有序的
     *
     * 2314 - 2314 - 1234 -1234
     *
     * @param nums
     * @return
     */
    public static int[] run(int[] nums){
        int k;
        for (int i = 1; i<nums.length; i++){
            int temp = nums[i];
            for (int j = i; j>0;j--){
                if (nums[j -1] > temp){
                    k = nums[j -1];
                    nums[j - 1] = temp;
                    nums[j] = k;
                }
            }
            System.out.println("第" + i + "次排序 : " + Arrays.toString(nums));
        }
        return nums;
    }

}
