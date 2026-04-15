package com.wx.study.collection;

import java.util.*;

public class HiddenIterator {
    private final Set<Integer> set=new HashSet<>();
//    private final Set<Integer> set= Collections.synchronizedSet(new HashSet<>());
    public synchronized void add(Integer i){
        set.add(i);
    }
    public synchronized void remove(Integer i) {
        set.remove(i);
    }
    public  void addTenThings(){
        Random r=new Random();
        for (int i = 0; i < 10; i++) {
            add(r.nextInt());
            System.out.println("debug : added ten elements to "+ set);      //
        }
    }
}
