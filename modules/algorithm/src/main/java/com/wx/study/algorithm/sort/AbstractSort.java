package com.wx.study.sort;

/**
 * @author wxli
 * @date 2021/8/10 22:53
 */
public abstract class AbstractSort {
    public abstract void sort(Comparable[] a);

    //比较
    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //交换
    public static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public static void show(Comparable[] a, int i) {
        int k = i + 1;
        if (k == a.length + 1) {
            System.out.print("结束排序\t");
        } else {
            System.out.print("第" + k + "次" + "排序\t");
        }
        for (int j = 0; j < a.length; j++) {
            System.out.print(a[j] + "");
        }
        System.out.println();

    }

    //检查数组是否有序
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }
}
