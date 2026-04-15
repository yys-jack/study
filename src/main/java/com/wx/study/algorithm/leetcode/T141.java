package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/linked-list-cycle/
 */
public class T141 {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        ListNode fast = head;   //快指针
        ListNode slow = head;   //慢指针
        //快指针一次走2步，慢指针一次走一步，如果有环，那么快慢指针一定会相遇
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
