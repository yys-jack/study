package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/merge-two-sorted-lists/
 *
 * todo
 * 出错次数     3
 */
public class T21 {

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //如果有一个空链表，则直接返回另一个
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        //新建一个链表
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (list1 != null && list2 != null) {
            //比较list1，list2.节点值大小
            //值小的放curr.next后面
            if (list1.val > list2.val) {
                curr.next = list2;
                list2 = list2.next;
            } else {
                curr.next = list1;
                list1 = list1.next;
            }
            curr = curr.next;
        }
        //然后把那个不为空的链表挂到新的链表中
        curr.next = list1 == null ? list2 : list1;
        return dummy.next;
    }

    //递归
    public ListNode mergeTwoLists_2(ListNode list1, ListNode list2) {
        //终止条件
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val <= list2.val) {
            list1.next = mergeTwoLists_2(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists_2(list1, list2.next);
            return list2;
        }
    }

}
