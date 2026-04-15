package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/koko-eating-bananas/
 */
public class T875 {
    //所有香蕉都要被吃完
    //最小速度 s 应是
    public int minEatingSpeed(int[] piles, int h) {
        int lo = 1;
        int hi = 0;
        //查找 max
        for (int pile : piles) {
            hi = Math.max(hi, pile);
        }
        int k = hi;
        //二分查找
        while (lo < hi) {
            int s = lo + (hi - lo) / 2;
            long time = getTime(piles, s);
            if (time <= h) {
                k = s;
                hi = s;
            } else {
                lo = s + 1;
            }
        }
        return k;
    }


    public long getTime(int[] piles, int s) {
        int time = 0;
        for (int pile : piles) {
            int curTime = (pile + s - 1) / s;
            time += curTime;
        }
        return time;
    }


}
