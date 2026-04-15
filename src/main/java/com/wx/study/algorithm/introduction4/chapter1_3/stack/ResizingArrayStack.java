package com.wx.study.introduction4.chapter1_3.stack;

import java.util.Iterator;

/**
 * 下压堆栈（数组实现）
 * 动态调整数组的大小以保持数组大小和栈大小之比小于常数
 *
 * @author wxli
 * @date 2021/8/12 21:47
 */
public class ResizingArrayStack<T> implements Iterable<T> {
    private T[] a = (T[]) new Object[1];
    private int N = 0;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int max) {
        //将栈移动到一个大小为max的新数组
        T[] temp = (T[]) new Object[max];
        for (int i = 0; i < N; i++)
            temp[i] = a[i];
        a = temp;
    }

    public void push(T t) {
        //将元素添加到栈顶
        if (N == a.length) resize(2 * a.length);
        a[N++] = t;
    }

    public T pop() {
        T t = a[--N];
        a[N] = null; //避免对象游离
        if (N > 0 && N == a.length / 4) resize(a.length / 2);
        return t;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReverserArrayIterator();
    }


    private class ReverserArrayIterator implements Iterator<T> {
        private int i = N;

        public boolean hasNext() {
            return i > 0;
        }

        public T next() {
            return a[--i];
        }

        public void remove() {

        }
    }
}
