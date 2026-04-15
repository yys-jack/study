package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/first-bad-version/
 */
public class T278 {
    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (!isBadVersion(mid)) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private boolean isBadVersion(int mid) {
        return true;
    }

}

