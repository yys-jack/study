package com.wx.study.leetcode;

import java.util.Arrays;

public class T1089 {
    public void duplicateZeros(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            //如果 第i位是0，则将 [i+1 ， n-1-1] 数复制到  [i+2,n-1]位置，再将i+1写为0，并将i指向后面第二个位置
            if (arr[i] == 0) {
                for (int j = n - 1; j > i; j--) {
                    arr[j] = arr[j - 1];
                }
                if (i < n - 1) {
                    arr[i + 1] = 0;
                }
                i++;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        new T1089().duplicateZeros(new int[]{1, 0, 3});
    }

}
