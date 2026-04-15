package com.wx.study.leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class T733 {
    /**
     * 广度优先搜索
     */
    int[] dx = {1, 0, 0, -1};
    int[] dy = {0, 1, -1, 0};

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int currColor = image[sr][sc];
        if (currColor == newColor) {         //终止条件
            return image;
        }
        int n = image.length, m = image[0].length;         //边界
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});                    //初始位置入队列
        image[sr][sc] = newColor;
        while (!queue.isEmpty()) {                         //队列不为空
            int[] cell = queue.poll();                     //删除头节点
            int x = cell[0], y = cell[1];                  //取出当前位置x,y
            for (int i = 0; i < 4; i++) {                  //上下左右移动移动
                int mx = x + dx[i], my = y + dy[i];
                if (mx >= 0 && mx < n && my >= 0 && my < m && image[mx][my] == currColor) {             //边界条件且当前位置颜色是否等于处置位置颜色
                    queue.offer(new int[]{mx, my});        //入队列
                    image[mx][my] = newColor;
                }
            }
        }
        return image;
    }

}
