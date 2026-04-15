package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
 *
 * 出错次数    2
 */
public class T19 {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        //增加哑节点，考虑通用情况
        ListNode dummy = new ListNode(0, head);
        int len = getLen(head);
        ListNode curr = dummy;
        int x = 1;                  //第一个元素序号为 1
        //遍历找到 x = len - n + 1 个元素，删除该元素后面一个元素即可
        while (x < len - n + 1) {
            curr = curr.next;
            x++;
        }
        curr.next = curr.next.next;
        return dummy.next;
    }

    //计算链表长度
    private int getLen(ListNode head) {
        int len = 0;
        while (head != null) {
            head = head.next;
            len++;
        }
        return len;
    }

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
}
