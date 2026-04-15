package com.wx.study.algorithm.huawei.0322;
package train;

import java.util.LinkedList;
import java.util.Queue;

public class Xijun {
    public static void main(String[] args) {
        int[][] arr = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        System.out.println(new Xijun().getXijun(arr));
    }

    /**
     *  考察队列操作
     * @param arr
     * @return
     */
    private int getXijun(int[][] arr) {
        Queue<int[]> nextQueue = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int k = 0; k < arr[0].length; k++) {
                if (arr[i][k] == 1) {
                    nextQueue.add(new int[] {i, k});
                }
            }
        }
        int remainNum = arr.length * arr[0].length - nextQueue.size();
        if (remainNum == 0 || nextQueue.size()==0) {
            return -1;
        }

        int day = 0;
        int[][] direction = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        while (remainNum > 0) {
            int size = nextQueue.size();
            for (int i = 0; i < size; i++) {
                int[] current = nextQueue.poll();
                for (int k = 0; k < direction.length; k++) {
                    int nextX = current[0] + direction[k][0];
                    int nextY = current[1] + direction[k][1];
                    if (isValid(nextX, nextY, arr) && arr[nextX][nextY] == 0) {
                        nextQueue.add(new int[] {nextX, nextY});
                        arr[nextX][nextY] = 1;
                        remainNum--;
                    }
                }
            }
            day++;
        }
        return day;
    }

    private boolean isValid(int x, int y, int[][] arr) {
        if (x >= 0 && x < arr.length && y >= 0 && y < arr[0].length) {
            return true;
        }
        return false;
    }

}
