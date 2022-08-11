package com.dwk.leetcode.寻找两个正序数组的中位数;

public class Answer {

    public static void main(String[] args) {

    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //合并 - 排序 - 求中位数
        double returnDouble = 0.0;
        int[] nums = new int[nums1.length + nums2.length];
        //
        for (int i = 0; i<nums.length;i++){
            if (i < nums1.length){
                nums[i] = nums1[i];
            }else{
                nums[i] = nums2[i - nums1.length];
            }
        }
        for(int i = 0;i<nums.length;i++){

        }
        return returnDouble;
    }
}
