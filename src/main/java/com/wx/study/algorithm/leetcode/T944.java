package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/delete-columns-to-make-sorted/
 */
public class T944 {
    public int minDeletionSize(String[] strs) {
        int size = 0;
        int col = strs[0].length();
        int row = strs.length;
        for (int i = 0; i < col; i++) {
            for (int j = 1; j < row; j++) {
                if (strs[j - 1].charAt(i) > strs[j].charAt(i)) {
                    size++;
                    break;
                }
            }
        }
        return size;
    }


    public static void main(String[] args) {
        System.out.println(new T944().minDeletionSize(new String[]{"cba", "daf", "ghi"}));
    }
}
