package com.wx.study.introduction4.chapter1_3.stack;

/**
 * 定容栈
 *
 * @author wxli
 * @date 2021/8/12 22:10
 */
public class FixedCapacityStack<T> {
    private T[] a;
    private int N;

    public FixedCapacityStack(int cap) {  //创建一个容量为cap的空栈
        a = (T[]) new Object[cap];
    }

    public boolean isEmpty() {   //栈是否为空
        return N == 0;
    }

    public int size() {         //栈中元素的数量
        return N;
    }

    public void push(T value) {      //向栈中添加元素
        a[N++] = value;
    }

    public T pop() {            //删除一个元素
        return a[--N];
    }

    public void isFull() {
        a = (T[]) new Object();
        N = 0;
    }
}
