package com.wx.study.algorithm.sort;


/**
 * 选择排序
 * ...                                   a[N]
 * i     min           j          1  3  2  4  5  8
 * 0      0        (0 -> n-1)     1
 * 1      1        (1 -> n-1)     1  2
 * 2      2        (2 -> n-1)     1  2  3
 * 3      ...
 * 4      ...
 * ...
 * 记录一个索引min初始值为i
 * j = i +1
 * 然后将a[j]--a[N-1]和a[min] 依次比较，
 * 每一次找最小,都需要比较（n-i-1）次
 * less(a[j],a[min]) ==> ture ==> min=j
 *
 * @author wxli
 * @date 2021/8/8 21:10
 */
public class Select extends AbstractSort {
    @Override
    public void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;                        //位置
            for (int j = i + 1; j < n; j++) {   //寻找最小的数
                if (less(a[j], a[min])) min = j;     //保存索引
            }
            //交换
            exch(a, min, i);
            show(a, i);
        }
    }
}
