package com.wx.study.introduction4.chapter1_1.exercise;

public class Example27 {
    public static double binomial(int N, int k, double p) {
        if (N == 0 && k == 0) return 1.0;
        if (N < 0 || k < 0) return 0.0;
        return (1.0 - p) * binomial(N - 1, k, p) + p * binomial(N - 1, k - 1, p);
    }

    private static final int N = 20;
    private static final int k = 10;
    private static double[][] temp = new double[N + 1][k + 1];

    public static void main(String[] args) {
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = -1.0;
            }
        }
        System.out.println(binomial2(N, k, 0.25));
    }

    public static double binomial2(int N, int k, double p) {
        if (N < 0 || k < 0) {
            return 0.0;
        } else if (N == 0 && k == 0) {
            if (temp[N][k] == -1.0)
                temp[N][k] = 1.0;
        } else {
            temp[N][k] = (1.0 - p) * binomial2(N - 1, k, p) + p * binomial2(N - 1, k - 1, p);
        }
        return temp[N][k];
    }

}
