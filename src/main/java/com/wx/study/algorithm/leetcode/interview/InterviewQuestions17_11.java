package com.wx.study.leetcode.interview;

/**
 * https://leetcode.cn/problems/find-closest-lcci/
 */
public class InterviewQuestions17_11 {
    public int findClosest(String[] words, String word1, String word2) {
        int frist = 0;
        int two = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                //记录 i 为 1
                frist = i;
            }
            if (words[i].equals(word2)) {
                //记录 i 为 2
                two = i;
            }
            if (frist != 0 && two != 0) {
                min = Math.min(min, Math.abs(frist - two));
            }
        }
        return min;
    }
}



