package com.wx.study.leetcode;


import java.util.*;

/**
 * https://leetcode.cn/problems/serialize-and-deserialize-bst/
 *
 * todo
 * 树前序中序后续遍历
 *
 */
public class T449 {

    /**
     * 后序遍历
     */
    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            postOrder(root, list);
            String str = list.toString();
            return str;
        }

        //
        private void postOrder(TreeNode root, List<Integer> list) {
            if (root == null) {
                return;
            }
            postOrder(root.left, list);
            postOrder(root.right, list);
            list.add(root.val);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data.isEmpty()) {
                return null;
            }
            String[] arr = data.split(", ");
            Deque<Integer> stack = new ArrayDeque<>();
            int length = arr.length;
            for (int i = 0; i < length; i++) {
                stack.push(Integer.parseInt(arr[i]));
            }
            return construct(Integer.MIN_VALUE, Integer.MAX_VALUE, stack);
        }

        private TreeNode construct(int lo, int hi, Deque<Integer> stack) {
            if (stack.isEmpty() || stack.peek() < lo || stack.peek() > hi) {
                return null;
            }
            Integer val = stack.pop();
            TreeNode root = new TreeNode(val);
            root.left = construct(val, hi, stack);
            root.right = construct(lo, val, stack);
            return root;
        }

        /**
         * 前序遍历
         */
        public class Codec_2 {

            // Encodes a tree to a single string.
            public String serialize(TreeNode root) {
                List<Integer> list = new ArrayList<>();
                preOrder(root, list);
                int n = list.size();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    sb.append(list.get(i));
                    if (i != n - 1) sb.append(",");
                }
                return sb.toString();
            }

            //先序遍历
            private void preOrder(TreeNode root, List<Integer> list) {
                if (root == null) {
                    return;
                }
                list.add(root.val);
                preOrder(root.left, list);
                preOrder(root.right, list);
            }

            public TreeNode deserialize(String s) {
                if (s == null) return null;
                String[] strs = s.split(",");
                return construct(0, strs.length - 1, strs);
            }

            private TreeNode construct(int leftVal, int rightVal, String[] strs) {
                if (leftVal > rightVal) return null;
                int j = leftVal + 1, t = Integer.parseInt(strs[leftVal]);
                TreeNode ans = new TreeNode(t);
                while (j <= rightVal && Integer.parseInt(strs[j]) <= t) j++;
                ans.left = construct(leftVal + 1, j - 1, strs);
                ans.right = construct(j, rightVal, strs);
                return ans;
            }
        }


        public class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;

            TreeNode(int x) {
                val = x;
            }
        }
        // Your Codec object will be instantiated and called as such:
        // Codec ser = new Codec();
        // Codec deser = new Codec();
        // String tree = ser.serialize(root);
        // TreeNode ans = deser.deserialize(tree);
        // return ans;
    }
}
