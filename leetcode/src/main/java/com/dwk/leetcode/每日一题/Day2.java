package com.dwk.leetcode.每日一题;

/**
 * @author duweikun
 * @date 2023/7/6  8:46
 * @description   两条有序链表合并为一条有序链表
 */
public class Day2 {

    public static void main(String[] args) {
        SortNode sortNode = new SortNode();
        sortNode.next = new SortNode();
        sortNode.next.next = new SortNode();
        sortNode.value = 1;
        sortNode.next.value = 2;
        sortNode.next.next.value = 4;

        SortNode sortNode2 = new SortNode();
        sortNode2.next = new SortNode();
        sortNode2.next.next = new SortNode();
        sortNode2.value = 1;
        sortNode2.next.value = 3;
        sortNode2.next.next.value = 4;

        SortNode result = new SortNode();
        handle(sortNode,sortNode2,result);
        while (result.hasNext()){
            result = result.next;
            System.out.println(result.value);
        }
    }

    public static void handle(SortNode l1, SortNode l2, SortNode result){
        if (l1 != null && l2 != null){
            if (l1.value <= l2.value){
                result.next = l1;
                handle(l1.next,l2,result.next);
            }else{
                result.next = l2;
                handle(l1,l2.next,result.next);
            }
        }

        if (l1 == null){
            result.next = l2;
            while (l2.next != null){
                result.next = l2.next;
            }
        }

        if (l2 == null){
            result.next = l1;
            while (l1.next != null){
                result.next = l1.next;
            }
        }
    }

}

class SortNode{
    SortNode next;

    int value;

    public SortNode(SortNode next, int value) {
        this.next = next;
        this.value = value;
    }

    public SortNode() {
    }

    public boolean hasNext(){
        return this.next != null;
    }

}
