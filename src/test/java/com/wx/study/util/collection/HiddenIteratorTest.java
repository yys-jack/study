package com.wx.study.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiddenIteratorTest {

    @Test
    void addTenThings() {
        HiddenIterator hiddenIterator = new HiddenIterator();
        new Thread(()->{
            hiddenIterator.addTenThings();
        }).start();
        new Thread(()->{
            hiddenIterator.addTenThings();
        }).start();
    }
}