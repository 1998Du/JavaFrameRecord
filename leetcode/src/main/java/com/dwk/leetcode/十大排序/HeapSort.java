package com.dwk.leetcode.十大排序;

import java.util.Arrays;

/**
 * @author duweikun
 * @date 2023/7/14  14:16
 * @description
 *
 * 堆排序
 *  堆（完全二叉树）：每个节点的值都大于等于其左右子节点的值，没有要求左右节点值的大小关系
 *  每次构建二叉树，将根节点挪到末尾，每次执行获取到数组内的最大数
 *
 *  从左至右从下至上调整树结构，从非叶子节点开始调整
 *
 * 1、将无序序列构建成一个堆，根据升序降序需求选择大顶堆、小顶堆
 * 2、将堆顶元素与末尾元素交换，将最大元素沉到数组末端
 * 3、重新调整结构，使其满足堆定义，继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤 直到整个序列有序
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] nums = {4,6,8,5,9};
        Arrays.stream(run(nums)).forEach(System.out::println);
    }

    public static int[] run(int[] nums){
        // 1、构建完整堆
        // 获取根节点个数
        for (int i = nums.length / 2 - 1; i>=0; i--){
            heap(nums,i,nums.length);
        }

        // 2、元素交换
        int temp;
        for (int j = nums.length -1; j>0; j--){
            temp = nums[j];
            nums[j] = nums[0];
            nums[0] = temp;
            // 3、重新调整结构
            heap(nums,0,j);
        }
        return nums;
    }

    /**
     * 构建堆（局部）
     */
    public static void heap(int[] nums,int rootIndex,int length){
        // 当前根节点的值
        int root = nums[rootIndex];
        // 用根节点的左叶子节点和右叶子节点比较
        for(int left = rootIndex * 2 + 1; left<length; left = left * 2 + 1){
            if(left + 1< length && nums[left] < nums[left+1]){
                // 左叶子节点小于右叶子节点
                left++;
            }
            if(nums[left] > root){
                // 叶子节点大于根节点
                nums[rootIndex] = nums[left];
                rootIndex = left;
            }else{
                break;
            }
        }
        nums[rootIndex] = root;
    }

}
