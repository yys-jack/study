package com.wx.study.introduction4.chapter1_3.bag;

import java.util.Iterator;

/**
 * 背包
 * 只有添加元素
 *
 * @author wxli
 * @date 2021/8/12 22:43
 */
public class Bag<T> implements Iterable<T> {
    private Node first;   //头节点
    private int N;

    private class Node {
        T value;
        Node next;
    }

    public void add(T value) {
        Node oleFirst = first;
        first = new Node();
        first.next = oleFirst;
        first.value = value;
        N++;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return first.next != null;
            }

            @Override
            public T next() {
                return first.value;
            }
        };
    }
}
