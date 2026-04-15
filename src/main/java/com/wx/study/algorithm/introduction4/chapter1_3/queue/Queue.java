package com.wx.study.introduction4.chapter1_3.queue;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * FIFO   先进先出队列
 *
 * @author wxli
 * @date 2021/8/11 23:21
 */
public class Queue<T> implements Iterable<T> {
    private Node first;   //头节点
    private Node last;    //尾节点
    private int N;

    private class Node {
        T value;
        Node next;
    }

    public boolean isEmpty() {
        return first == null; //或者N==0;
    }

    public int size() {
        return N;
    }

    public void enqueue(T value) {
        //向表尾添加元素
        Node oleLast = last;
        last = new Node();
        last.next = null;
        last.value = value;
        if (isEmpty()) first = last;
        else oleLast.next = last;
        N++;
    }

    public T dequeue() {
        //表头移除元素
        T value = first.value;
        first = first.next;
        if (isEmpty()) last = null;
        N--;
        return value;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = first;
            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public T next() {
                T item= current.value;
                current=current.next;
                return item;
            }
        };
    }
}
