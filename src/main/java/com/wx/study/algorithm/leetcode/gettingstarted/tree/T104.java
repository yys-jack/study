package com.wx.study.leetcode.gettingstarted.tree;

import java.util.LinkedList;

/**
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree/
 */
public class T104 {

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

    //dfs
    //树的高度，等于方法压栈次数
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = maxDepth(root.left);
        int r = maxDepth(root.right);
        return Math.max(l, r) + 1;
    }

    //bfs
    //todo
    public int maxDepth_2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int res = 0;
        while (!queue.isEmpty()) {
            //每层的个数
            int size = queue.size();
            while (size-- > 0) {
                TreeNode cur = queue.pop();
                if (cur.left != null) {
                    queue.offer(root.left);
                }
                if (cur.right != null) {
                    queue.offer(root.right);
                }
            }

            res++;
        }
        return res;
    }
}
