package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/largest-triangle-area/
 */
public class T812 {
    public double largestTriangleArea(int[][] points) {
        int n = points.length;
        double result = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    result = Math.max(result, triangleArea(points[i][0], points[i][1], points[j][0], points[j][1], points[k][0], points[k][1]));
                }
            }
        }
        return result;
    }

    //三角形面积公式，行列式表示
    public double triangleArea(int x1, int y1, int x2, int y2, int x3, int y3) {
        return 0.5 * Math.abs(x1 * y2 + x2 * y3 + x3 * y1 - x1 * y3 - x2 * y1 - x3 * y2);
    }

    public static void main(String[] args) {
        int[][] ints = {{1, 0}, {0, 0}, {0, 1}};
        new T812().largestTriangleArea(ints);
    }
}
