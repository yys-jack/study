package com.wx.study.algorithm.huawei.0322;
package train;

public class MaxSeq {
    public static void main(String[] args) {
        // int[] arr = {1,2,3,4,2};
        // int sum = 6;
        // System.out.println(new MaxSeq().getMaxSeq(arr, sum));

        int[] arr = {1,1,1,1,1,1,1,7};
        int sum = 9;
        System.out.println(new MaxSeq().getMaxSeq(arr, sum));

        // int[] arr = {100,0,0,0,4,50,0,0};
        // int sum = 54;
        // System.out.println(new MaxSeq().getMaxSeq(arr, sum));

        // int[] arr = {1, 1, 1, 1, 1};
        // int sum = 4;
        // System.out.println(new MaxSeq().getMaxSeq(arr, sum));
    }

    /**
     * 滑窗算法  正整数、求连续
     *
     * @param arr
     * @param sum
     * @return
     */
    public int getMaxSeq(int[] arr, int sum) {
        int left = 0;
        int right = 0;
        int maxLen = -1;

        int sumTmp = 0;
        while (left < arr.length) {
            // 如果题目不是说正整数，可以是0，则需要条件 加=号；保持right索引和sum实际步伐一致，需要相加判断 sumTmp + arr[right] <= sum
            while (right < arr.length && sumTmp + arr[right] <= sum) {
                sumTmp += arr[right];
                right++;
                if (sumTmp == sum) {
                    if ((right - left) > maxLen) {
                        maxLen = right - left;
                    }
                }
            }
            sumTmp -= arr[left];
            left++;
        }
        return maxLen;
    }
}
