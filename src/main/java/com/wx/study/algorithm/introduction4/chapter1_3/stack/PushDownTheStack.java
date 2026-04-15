package com.wx.study.introduction4.chapter1_3.stack;

/**
 * 下压堆栈（链表实现）
 *
 * @author wxli
 * @date 2021/8/12 22:17
 */
public class PushDownTheStack<T> {

    private Node first;
    private int N;

    public void push(T t) {
        Node oleFirst = first;
        first = new Node();
        first.value = t;
        first.next = oleFirst;
        N++;
    }

    public T pop() {
        T t = first.value;
        first = first.next;
        N--;
        return t;
    }

    public boolean isEmpty() {
        return N == 0;   //或者first==null;
    }

    public int size() {
        return N;
    }

    private class Node {
        private T value;
        private Node next;
    }
}
