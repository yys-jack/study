package com.wx.study.leetcode.gettingstarted.tree;

/**
 * https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/
 * todo
 */
public class T108 {
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

    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode node = new TreeNode();
        return node;
    }
}
