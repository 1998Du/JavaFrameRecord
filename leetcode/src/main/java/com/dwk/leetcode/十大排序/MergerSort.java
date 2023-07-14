package com.dwk.leetcode.十大排序;

import java.util.Arrays;

/**
 * @author duweikun
 * @date 2023/7/14  10:05
 * @description
 *
 *  归并排序（递归 + 合并）
 */
public class MergerSort {

    public static void main(String[] args) {
        // 前半段有序后半段也有序的数组
        int[] nums = {1,4,6,9,3,4,5};
        Arrays.stream(run(nums, 0, nums.length-1)).forEach(System.out::println);
    }

    /**递归*/
    public static int[] run(int[] nums,int left,int right){
        if (left == right){
            return nums;
        }
        // 分成两半
        int mid = (left+right)/2;
        // 左边排序
        run(nums,left,mid);
        // 右边排序
        run(nums,mid + 1,right);
        // 归并
        return merger(nums,left,mid+1,right);
    }

    /**合并*/
    public static int[] merger(int[] nums,int left,int right,int bound){
        // 构建一个新数组储存结果
        int[] result = new int[bound - left + 1];
        int end = right -1;
        int i = left;
        int j = right;
        int k = 0;
        while (i<=end && j<=bound){
            result[k++] = nums[i] <= nums[j] ? nums[i++] : nums[j++];
        }
        while (i<=end){
            result[k++] = nums[i++];
        }
        while (j<=right){
            result[k++] = nums[j++];
        }
        return result;
    }

}
