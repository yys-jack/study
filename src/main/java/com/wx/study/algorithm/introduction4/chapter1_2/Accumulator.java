package com.wx.study.introduction4.chapter1_2;

public class Accumulator {
    private double total;
    private int N;

    public void addDataValue(double val) {
        N++;
        total += val;
    }

    public double mean() {
        return total / N;
    }

    @Override
    public String toString() {
        return "Accumulator{" +
                "total=" + total +
                ", N=" + N +
                '}';
    }
}
