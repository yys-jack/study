package com.wx.study.introduction4.chapter1_1.exercise;

import java.util.Arrays;

public class Example30 {
    public static void main(String[] args) {
        int N = 3;
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) a[i][j] = true;
            }
        }
        System.out.println(Arrays.deepToString(a));
    }

}
