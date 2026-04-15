package com.wx.study.leetcode.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/successor-lcci/
 */
public class InterviewQuestions04_06 {


    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        List<TreeNode> list = new ArrayList<>();
        inOrder(root, list);
        int i = list.indexOf(p);
        if (i + 1 < list.size()) {
            return list.get(i + 1);
        }
        return null;
    }

    public void inOrder(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        inOrder(root.left, list);
        list.add(root);
        inOrder(root.right, list);
    }
    //前序遍历
    public void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val);
        inOrder(root.left);
        inOrder(root.right);
    }

    //中序遍历
    public static void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.val);
        inOrder(root.right);
    }

    public void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val);
        inOrder(root.left);
        inOrder(root.right);
    }

    public static void main(String[] args) {
        TreeNode treeNode = new InterviewQuestions04_06.TreeNode(2);
        treeNode.left = new TreeNode(1);
        treeNode.left.left = null;
        treeNode.right = new TreeNode(3);

        inOrder(treeNode);
    }
}



