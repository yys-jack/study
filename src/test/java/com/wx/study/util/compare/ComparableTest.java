package com.wx.study.compare;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComparableTest {
    @Test
    public void testCompare(){
        System.out.println(Integer.compare(1, 1));
        System.out.println(Integer.compare(1, 2));
        List<Integer> list = new LinkedList<>();
        list.add(2);
        list.add(1);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
    }

}