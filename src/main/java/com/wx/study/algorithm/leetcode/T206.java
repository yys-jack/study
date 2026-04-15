package com.wx.study.leetcode;

import java.util.Stack;

/**
 * https://leetcode.cn/problems/reverse-linked-list/
 *
 * 计数 3
 */
public class T206 {

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

    //   head    next
    //     1  ->  2    -> 3
    //   null
    //  newHead

    //  --->
    //将  head.next = newHead
    //    newHead  = head

    //   head    next
    //    2    -> 3
    //    1 -> null
    //  newHead

    //双链表
    public ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        while (head != null) {

            ListNode next = head.next;

            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }

    //栈
    public ListNode reverseList_2(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        //将链表入栈
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        if (stack.isEmpty()) {
            return null;
        }
        //栈元素全部出栈
        ListNode node = stack.pop();
        ListNode dummy = node;
        while (!stack.isEmpty()) {
            ListNode temp = stack.pop();
            node.next = temp;
            node = node.next;
        }
        //最后一个节点
        node.next = null;
        return dummy;
    }

    //递归
    public ListNode reverseList_3(ListNode head) {
        return reverseListInt_1(head, null);
    }

    //尾递归写法
    public ListNode reverseListInt_1(ListNode head, ListNode newHead) {
        //终止条件
        if (head == null) {
            return newHead;
        }
        //
        ListNode next = head.next;
        head.next = newHead;
        return reverseListInt_1(next, head);
    }

    //递归写法
    public ListNode reverseList_4(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        ListNode reverse = reverseList_4(next);
        //reverse代表反转后的链表
        //反转完之后next肯定是reverse链表的尾节点
        //head挂到next的后面就完成了反转
        next.next = head;
        //head相当于变成了尾节点，尾节点都是空的
        //否者就会成环
        head.next = null;
        return reverse;
    }


}
