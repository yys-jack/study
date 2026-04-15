package com.wx.study.leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class T695 {
    public int maxAreaOfIsland(int[][] grid) {
        int ans = 0;
        for (int i = 0; i != grid.length; i++) {
            for (int j = 0; j != grid[0].length; j++) {
                //判断该位置是否为陆地
                if (grid[i][j] != 0) {
                    ans = Math.max(ans,dfs(grid, i, j));
                }
            }
        }
        return ans;
    }

    int dfs(int[][] grid, int x, int y) {
        if (x < 0 || y < 0 || x == grid.length || y == grid[0].length || grid[x][y] != 1) {       //终止条件
            return 0;
        }
        grid[x][y] = 0;
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        int ans = 1;
        for (int i = 0; i != 4; i++) {
            int mx = x + dx[i], my = y + dy[i];
            ans += dfs(grid, mx, my);
        }
        return ans;
    }

    //广度优先遍历
    public int maxAreaOfIsland_2(int[][] grid) {
        int ans = 0;

        for (int i = 0; i != grid.length; i++) {
            for (int j = 0; j != grid[0].length; j++) {
                int curr = 0;
                Queue<Integer> queuei = new LinkedList<>();
                Queue<Integer> queuej = new LinkedList<>();
                queuei.offer(i);
                queuej.offer(j);
                while (!queuei.isEmpty()) {
                    //取出当前位置值
                    int cur_i = queuei.poll();
                    int cur_j = queuej.poll();
                    if (cur_i < 0 || cur_j < 0 || cur_i == grid.length || cur_j == grid[0].length || grid[cur_i][cur_j] != 1) {
                        continue;
                    }
                    ++curr;
                    grid[cur_i][cur_j] = 0;     //将当前位置修改置为0
                    //当前位置 上下左右移动
                    int[] di = {0, 0, 1, -1};
                    int[] dj = {1, -1, 0, 0};
                    for (int index = 0; index != di.length; ++index) {
                        int index_i = di[index] + cur_i;
                        int index_j = dj[index] + cur_j;
                        queuei.offer(index_i);
                        queuej.offer(index_j);
                    }
                }
                ans = Math.max(ans, curr);
            }
        }
        return ans;
    }
}
