package com.wx.study.leetcode.offer;

import java.util.ArrayDeque;
import java.util.Queue;

public class II_041_MovingAverage {
    private int size;
    private Queue<Integer> queue;

    public II_041_MovingAverage(int size) {
        this.size = size;
        this.queue = new ArrayDeque<>(size);
    }

    public double next(int val) {
        if (queue.isEmpty() || queue.size() < size) {
            queue.offer(val);
        } else {
            queue.poll();
            queue.offer(val);
        }
        return queue.stream().mapToDouble(value -> value).sum() / queue.size();
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> deque = new ArrayDeque<>(3);
        deque.add(1);
        deque.add(10);
//        deque.add(3);

        System.out.println(deque.stream().reduce(Integer::sum).get() / deque.size());
    }
}