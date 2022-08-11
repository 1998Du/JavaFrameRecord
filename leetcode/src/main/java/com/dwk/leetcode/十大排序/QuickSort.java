package com.dwk.leetcode.十大排序;


import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

    private static int time;

    public static void main(String[] args) {
        int[] nums = {5,3,4,2,1,6,8,7,9,0};
        System.out.println(Arrays.toString(nums));
        run(nums,0,nums.length-1);
    }

    public static int[] run(int[] nums,int begin,int end){
        if (begin < end) {
            // 基准数
            int key = nums[begin];
            int i = begin;
            int j = end;
            while (i < j) {
                while (i < j && nums[j] > key) {
                    j--;
                }
                if (i < j) {
                    nums[i] = nums[j];
                    i++;
                }
                while (i < j && nums[i] < key) {
                    i++;
                }
                if (i < j) {
                    nums[j] = nums[i];
                    j--;
                }
            }
            nums[i] = key;
            time++;
            System.out.println("第" + time + "次排序 : " + Arrays.toString(nums) + "基准数:" + key);
            run(nums, begin, i - 1);
            run(nums, i + 1, end);
        }
        return nums;
    }

}
