//package com.wx.demo.datastructure.tree;
//
//
///**
// * @author wxli
// * @date 2021/8/26 17:17
// */
//public class RedBlackBST<Key extends Comparable<Key>, Value> {
//    private static final boolean RED = true;
//    private static final boolean BLACK = false;
//    private Node root;
//
//    private class Node {
//        Key key;
//        Value val;
//        Node left, right;
//        int N;
//        boolean color;
//
//        Node(Key key, Value val, int N, boolean color) {
//            this.key = key;
//            this.val = val;
//            this.N = N;
//            this.color = color;
//        }
//    }
//
//    private boolean isRed(Node x) {
//        if (x == null) return false;
//        return x.color == RED;
//    }
//
//    //左旋，     右链接为红色才需要左旋，
//    //旋转不会改变颜色，只会改变父子节点
//    //旋转是为了保证红黑树特性，有序性和平衡性。
//    Node rotateLeft(Node h) {
//        Node x = h.right;
//        h.right = x.left;
//        x.left = h;
//        x.color = h.color;
//        h.color = RED;
//        x.N = h.N;
//        h.N = 1 + size(h.left) + size(h.right);
//        return x;
//    }
//
//    Node rotateRight(Node h) {
//        Node x = h.left;
//        h.left = x.right;
//        x.right = h;
//        x.color = h.color;
//        h.color = RED;
//        x.N = h.N;
//        h.N = 1 + size(h.left) + size(h.right);
//        return x;
//    }
//
//    private int size(Node x) {
//        if (x == null) return 0;
//        return x.N;
//    }
//
//    public void put(Key key, Value val) {
//        root = put(root, key, val);
//        root.color = BLACK;
//    }
//
//    private Node put(Node h, Key key, Value val) {
//        if (h == null)   //标准的插入，和父节点用红链接相连
//            return new Node(key, val, 1, RED);
//        int cmp = key.compareTo(h.key);
//        if (cmp < 0) h.left = put(h.left, key, val);
//
//        else if (cmp > 0) h.right = put(h.right, key, val);
//        else h.val = val;
//
//        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
//        if (isRed(h.left) && isRed(h.left.left)) h = rotateLeft(h);
//        if (isRed(h.left) && isRed(h.right)) flipColors(h);
//
//        h.N = 1 + size(h.left) + size(h.right);
//        return h;
//    }
//
//}
