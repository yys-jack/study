package com.wx.study.leetcode.gettingstarted.tree;

/**
 * https://leetcode.cn/problems/delete-node-in-a-bst/solution/
 * todo
 */
public class T450 {
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

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        //查找key在哪
        if (root.val < key) {
            return deleteNode(root.right, key);
        } else if (root.val > key) {
            return deleteNode(root.left, key);
        } else {
            //该节点为叶子节点
            if (root.left == null && root.right == null) {
                return null;
            }
            //节点左子树为空
            if (root.left == null) {
                return root.right;
            }
            //节点右子树为空
            if (root.right == null) {
                return root.left;
            }
            //节点左右子树都不为空,那么此时需要合并左右子树

        }
        return root;

    }
}
