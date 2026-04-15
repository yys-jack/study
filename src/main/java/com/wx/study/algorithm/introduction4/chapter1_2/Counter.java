package com.wx.study.introduction4.chapter1_2;

public class Counter {
    private final String id;
    private int count;

    public Counter(String id) {
        this.id = id;
    }

    public void increment() {
        count++;
    }

    public int tally() {
        return count;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "id='" + id + '\'' +
                ", count=" + count +
                '}';
    }
}
