package com.wx.study.leetcode;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/find-right-interval/solution/
 */
public class T436 {
    public int[] findRightInterval(int[][] intervals) {
        int n = intervals.length;
        int[][] startIntervals = new int[n][2];
        for (int i = 0; i < n; i++) {
            startIntervals[i][0] = intervals[i][0];
            startIntervals[i][1] = i;
        }
        Arrays.sort(startIntervals, (o1, o2) -> o1[0] - o2[0]);
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = n - 1;
            //二分查找，
            while (left < right) {
                int mid = (left + right) / 2;
                if (startIntervals[mid][0] >= intervals[i][1]) right = mid;
                else left = mid + 1;
            }
            res[i] = startIntervals[right][0] >= intervals[i][1] ? startIntervals[right][1] : -1;
        }


        return res;
    }

    public static void main(String[] args) {
        new T436().findRightInterval(new int[][]{{3, 4}, {2, 3}, {1, 2}});
    }
}
