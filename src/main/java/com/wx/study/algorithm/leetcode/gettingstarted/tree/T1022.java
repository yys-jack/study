package com.wx.study.leetcode.gettingstarted.tree;


/**
 * https://leetcode.cn/problems/sum-of-root-to-leaf-binary-numbers/
 */
public class T1022 {
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
    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }

    //深度优先
    //后序遍历
    public int dfs(TreeNode node, int val) {
        if (node == null) {
            return 0;
        }
        val = (val << 1) | node.val;
        if (node.left == null || node.right == null) {
            return val;
        }
        return dfs(node.left, val) + dfs(node.right, val);
    }
}
