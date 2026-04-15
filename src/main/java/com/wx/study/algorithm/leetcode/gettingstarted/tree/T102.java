package com.wx.study.leetcode.gettingstarted.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnldjj/
 */
public class T102 {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    //广度优先算法
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            //每层节点数
            int size = queue.size();
            ////subList存储的是每层的结点值
            List<Integer> subList = new ArrayList<>();
            while (size-- > 0) {
                //出队列，
                TreeNode curr = queue.poll();
                //将数放入subList
                subList.add(curr.val);
                //左右节点如果不为空就加入队列中
                if (curr.left != null) {
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    queue.offer(curr.right);
                }
            }
            list.add(subList);
        }
        return list;
    }
}
