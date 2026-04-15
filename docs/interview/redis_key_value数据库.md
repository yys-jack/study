```c
typedef struct redisObject{
    //类型
    unsigned type:4;
    //编码
    unsigned encoding:4;
    //指向底层实现数据结构的指针
    void *ptr;
    // ... 
    //引用计数，用于内存回收，引用回收算法
    int refcount;
    //记录对象最后一次被命令程序访问的时间
    unsigned lru:22;
}
```

<!-- @import "./data_structure/string.puml" -->


#### list

<!-- @import "./data_structure/list.puml" -->

<!-- @import "./image/list_1.png" -->

<!-- @import "./image/list_2.png" -->

<!-- @import "./image/list_3.png" -->


当列表对象可以同时满足以下两个条件时，列表对象使用ziplist编码，否则使用linkedlist
1. 列表对象保存的所有字符串元素的长度都小于64字节
2. 列表对象保存的元素数量小于512 个。

#### hash

<!-- @import "./data_structure/hash.puml" -->

<!-- @import "./image/hash_1.png" -->

<!-- @import "./image/hash_2.png" -->

当哈希对象同时满足以下两个条件时;哈希对象使用 ziplist 编码
1. 哈希对象保存的所有键值对的键和值的字符串的长度都小于64字节
2. 哈希对象保存的键值对数量小于512 个。

#### set

<!-- @import "./data_structure/set.puml" -->

<!-- @import "./image/set_1.png" -->

<!-- @import "./image/set_2.png" -->
当集合对象可以同时满足以下两个条件时，使用intset，否者使用ht
1. 集合对象保存的所有元素都是整数值;
2. 集合对象保存的元素数量不超过512个。

#### zset

<!-- @import "./data_structure/zset.puml" -->


<!-- @import "./image/zset_1.png" -->
<!-- @import "./image/zset_2.png" -->

当有序集合对象同时满足以下两个条件，使用ziplist
1. 有序集合保存的元素数量小于128个;
2. 有序集合保存的所有元素成员的长度都小于64字节。


#### 参考

[redis官网](https://redis.io/)
[redis中文网](http://www.redis.cn/)
[redis value的5中类型](https://www.cnblogs.com/yhq1314/p/10000971.html)