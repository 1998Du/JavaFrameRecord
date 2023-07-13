package com.dwk.leetcode.十大排序;

import java.util.Arrays;

/**
 * @author duweikun
 * @date 2023/7/10  10:15
 * @description
 * 希尔排序   -  改进的插入排序
 *      分组插入排序,递减增量排序算法
 *
 */
public class HillSort {

    public static void main(String[] args) {
        int[] nums = {1,3,4,2,5,6,8,7,9,12,3,22,344,45,345,2,45,135,12,3451,25,125,125,4325,145,14,5};
        Arrays.stream(run(nums)).forEach(System.out::println);
    }

    /**解法1*/
    public static int[] run(int[] nums){
        int space = nums.length / 2;
        while (space >= 1){
            for(int j = 0; j<nums.length; j++) {
                int value = 0;
                int index = 0;
                int capacity = 0;
                while (value < nums.length){
                    capacity++;
                    value += space;
                }
                int[] sortNums = new int[capacity];
                int[] indexes = new int[capacity];
                value = 0;
                while (value < nums.length){
                    sortNums[index] = nums[value];
                    indexes[index] = value;
                    index++;
                    value += space;
                }
                int[] ints = insertSort(sortNums);
                for(int i = 0; i< indexes.length; i++){
                    nums[indexes[i]] = ints[i];
                }
            }
            space = space / 2;
        }
        return nums;
    }

    /**插入排序*/
    public static int[] insertSort(int[] nums){
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
        }
        return nums;
    }


    /**解法2*/
    public static int[] hillSort(int[] arr){
        // 间隔循环
        for (int location = arr.length >> 1; location > 0; location /= 2) {
            // 直接改造插入排序
            for (int i = location; i < arr.length; i++) {
                for (int j = i; j > location - 1; j -= location) {
                    swap(arr, i, j);
                }
            }
        }
        return arr;
    }

    public static void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }




}
