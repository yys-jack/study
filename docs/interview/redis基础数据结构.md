<!-- @import "./puml/val数据结构和底层数据结构对应关系.puml" -->

#### 简单动态字符串
```c
typedef struct sdshdr{
    //记录buf数组中已使用字节的数量
    //等于SDS所保存字符串的长度
    int len;
    //记录buf数组中未使用字节的数量
    int free;
    //字节数组，用于保存字符串
    char buf[];
}sdshdr;
```
<!-- @import "./puml/sds.puml" -->


#### 链表

```c
typedef struct listNode{
    //前置节点
    listNode *prev;
    //后置节点
    listNode *next;
    //节点的值
    void *value;
}listNode;
```


```c
typedef struct list{
    //表头节点
    listNode *head;
    //表尾节点
    listNode *tail;
    //链表所包含的节点数量
    unsigned long len;
    //节点值复制函数
    void *(*dup) (void *ptr);
    //节点值释放函数
    void *(*free) (void *ptr);
    //节点值对比函数
    int (*match) (void *ptr,void *key);
}list;
```

#### 哈希表

```c
typedef struct dictht{
    //哈希表数组
    dictEntry **table;
    //哈希表大小    
    unsigned long size;
    //哈希表大小掩码，用于计算索引值
    //总是等于size-1
    unsigned long sizemask;
    //该哈希表已有节点数量
    unsigned long used;
}dictht;
```


#### 字典
```c
typedef struct dictEntry{
    //键
    void *key;
    //值
    union{
        void *val;
        uint64_t u64;
        int64_t s64;
    }v;
    //指向下个哈希表节点，形成链表
    struct dictEntry *next;
}dictEntry;
```

```c
typedef struct dict{
    //类型特定函数
    dictType *type;
    //私有数据
    void *privdata;
    //哈希表
    dictht ht[2];
    //rehash 索引
    //当rehash 不在进行时，值为-1
    int trehashindex; /* rehashing not in progress if rehashindex == -1 */
}dict;
```

```c
typedef struct dictType{
    //计算哈希值的函数
    unsigned int (*hashFuncation) (const void *key);
    //复制键的函数 
    void *(*keyDup) (void *privdata, const void *key);
    //复制值的函数
    void *(*valDup) (void *privdata, const void *obj);
    //对比键的函数
    int (*keyCompare) (void *privdata,const void *key1,const void *key2);
    //销毁键的函数
    void (*keyDestructor) (void *privdata,void *key);
    //销毁值的函数
    void (*valDestructor) (void *privdata,void *obj);
}
```

<!-- @import "./image/字典_1.png" -->


#### 跳跃表

```c
typedef struct zskiplistNode{
    //后退指针
    struct zskiplistNode *backward;
    //分值
    double socre;
    //成员变量
    robj *obj;
    //层
    struct zskiplistLevel{
        //前进指针
        struct zskiplistNode *forward;
        //跨度
        unsigned int span;
    } level[];
} zskiplistNode;
```

```c
typedef struct zskiplist{
    struct skiplistNode *header, *tail;
    //表中节点数量
    unsigned long length;
    //表中层数最大的节点的层数
    int level;
} zskiplist;

```
 
<!-- @import "./image/跳跃表_1.png" -->

#### 整数集合

```c
typedef struct intset{
    //编码方式
    uint32_t encoding;
    //集合包含的元素数量
    uint32_t length;
    //保存元素的数组
    int8_t contents[];
}intset;
```

<!-- @import "./image/整数集合_1.png" -->

##### 特点

- contents 数组真正的类型取决于encoding 属性的值
- contents 有序，从大到小排列



#### 压缩列表

```c
typedef struct ziplist{
    //整个压缩列表占用字节数，
    //在对压缩列表进行重新分配，或计算zlend的位置时使用
    uint32_t  zlbytes;
    //记录压缩列表表尾节点距离压缩列表的起始位置有多少字节
    //通过这个偏移量，程序无需遍历整个压缩列表就可以确定表尾字节的地址
    uint32_t  zltail;
    //节点数量小于65535，直接记录实际值
    //大于65535，需要遍历整个压缩列表
    uint16_t zllen;
    //压缩列表包含各个节点，节点的长度由节点保存的内容决定
    列表节点 entryX;
    //特殊值0xFF(十进制255)，用于标记压缩列表的末端 
    uint8_t zlend; 
}
```

```c
typedef struct ziplistNode{
    //记录前一个节点的长度
    //如果前一个节点的长度小于254，那么 previous_entry_length长度为一个字节
    //如果前一个节点的长度大于254，那么 previous_entry_length长度为5个字节，
    //第一个字节为0xFE(10进制254)，后面4个字节用于保存前一个节点的长度
    previous_entry_length
    //记录节点content 属性保存数据的类型以及长度
    //1字节、两字节、或者五字节，值的最高位为00、01、10的是字节数组的编码
    encoding

    //
    content
}
```


<!-- @import "./image/压缩列表_1.png" -->

<!-- @import "./image/压缩列表_2.png" -->

 #### 对象  

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
}robj;
```



#### 参考

[redis设计与实现](C:\Users\yy\Desktop\资料\redis\redis设计与实现.pdf)
