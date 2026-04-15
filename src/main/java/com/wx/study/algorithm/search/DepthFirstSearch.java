package com.wx.study.search;

import com.wx.study.introduction4.chapter1_3.graph.Graph;

/**
 * 走迷宫
 * 选择一条没有标记过的通道，在你走过的路上铺一条绳子
 * 标记所有你第一次路过的路口和通道
 * 当来到一个标记过的路口时（用绳子）回退到上一个路口
 * 当回退到的路口已没有可走的通道时继续回退
 *
 *
 * 深度优先搜索
 *
 * @author wxli
 * @date 2021/9/3 14:54
 */
public class DepthFirstSearch {
    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph G, int s) {
        this.marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    public boolean marked(int w){
        return marked(w);
    }

    public int count(){
        return count;
    }
}
