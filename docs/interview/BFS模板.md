https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/solution/tian-chong-mei-ge-jie-dian-de-xia-yi-ge-you-ce-2-4/

```java

    void bfs(){
        //初始化队列，同时将第一层节点加入队列中
        Queue<String> queue= new ArrayDeque<>();
        queue.offer(start);
        
        //外层循环迭代的是层数
        while(!queue.isEmpty()){
            //记录当前队列大小
            int sz = queue.size();
            //遍历这一层的所有节点
            for(int i=0;i<sz;i++){
                //从队首取出元素
                String curr = queue.poll();
                //连接
                //拓展下一层节点
            }
        }
    }
```

