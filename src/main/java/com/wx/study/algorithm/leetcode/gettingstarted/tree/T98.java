package com.wx.study.leetcode.gettingstarted.tree;

import java.util.*;

/**
 * https://leetcode.cn/problems/validate-binary-search-tree/
 *
 * 出错次数 todo  2
 */
public class T98 {

    static class TreeNode {
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

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        isValidBST(root);
    }

    //根节点比左子节点大，比右子节点小
    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static boolean isValidBST(TreeNode node, long lo, long up) {
        if (node == null) {
            return true;
        }
        //左子树取值范围(-,lo)        lo代表子树父节点值
        //右之树取值范围(up,+)

        // 父节点     val   4
        // 左子树 left  val   5       取值范围(-,4)
        // 左子树 if (left  val  >=   parent.val)           return false

        // 右子树 right val   3       取值范围(4,+)
        // 右子树 if (right.val  <=   parent.val)           return false
        if (node.val <= lo || node.val >= up) {
            return false;
        }
        return isValidBST(node.left, lo, node.val) && isValidBST(node.right, node.val, up);

    }

    //非递归，中序遍历
    public boolean isValidBST_3(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        double inorder = Double.MAX_VALUE;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            stack.pop();
            if (root.val <= inorder) {
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }

    //中序遍历有序
    public boolean isValidBST_2(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inOrder(root, list);
        return isSorted(list);
    }

    //二叉搜索树中序遍历有序
    public void inOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        inOrder(root.left, list);
        list.add(root.val);
        inOrder(root.right, list);
    }

    //验证list是否有序
    public boolean isSorted(List<Integer> list) {
        if (list.isEmpty() || list.size() == 1) {
            return true;
        }
        Iterator<Integer> iter = list.iterator();
        Integer current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (previous.compareTo(current) >= 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

}
