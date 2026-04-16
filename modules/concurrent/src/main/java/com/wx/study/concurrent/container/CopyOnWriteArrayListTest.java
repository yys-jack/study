package com.wx.study.container;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {
    public static void main(String[] args) {

        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        copyOnWriteArrayList.add("first");
        copyOnWriteArrayList.add("second");

        Iterator<String> iterator = copyOnWriteArrayList.iterator();

        copyOnWriteArrayList.add("third");

//        iterator = copyOnWriteArrayList.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        //output:
        //遍历的时候会获得当前数组对象的一个拷贝，快照
        //每个线程都将获得当前时刻的一个快照


    }
}
