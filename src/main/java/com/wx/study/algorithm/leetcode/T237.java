package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/delete-node-in-a-linked-list/
 */
public class T237 {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}



