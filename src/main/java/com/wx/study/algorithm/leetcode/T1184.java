package com.wx.study.leetcode;

import java.util.Arrays;

public class T1184 {
    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
        int a = 0;
        int sum = Arrays.stream(distance).sum();
        if (start > destination) {
            int temp = start;
            start = destination;
            destination = temp;
        }
        for (int i = start; i < destination; i++) {
            a += distance[i];
        }
        int b = sum - a;
        return Math.min(a, b);


    }
}
