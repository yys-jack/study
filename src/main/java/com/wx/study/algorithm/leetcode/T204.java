package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/count-primes/
 */
public class T204 {
    //O(n^2)
    public int countPrimes(int n) {
        //质数，一个数只能被1和本身整除
        int num = 0;
        //当前数能被多少数整除
        int curNum = 0;
        for (int i = 1; i < n; i++) {
            for (int j = i; j >= 1; j--) {
                if (i % j == 0) {
                    curNum++;
                    if (curNum > 2) {
                        break;
                    }
                }
            }
            if (curNum == 2) {
                num++;
            }
            curNum = 0;
        }
        return num;
    }

    //枚举
    public int countPrimes_2(int n) {
        //质数，一个数只能被1和本身整除
        int num = 0;
        for (int i = 2; i < n; i++) {
            num += isPrime(i) ? 1 : 0;
        }
        return num;
    }

    public boolean isPrime(int x) {
        for (int i = 2; i * i < x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
