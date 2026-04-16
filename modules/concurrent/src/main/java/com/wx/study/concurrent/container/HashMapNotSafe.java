package com.wx.study.concurrent.container;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 验证hashMap线程不安全
 */
public class HashMapNotSafe {

    public static void main(String[] args) {

        final Map<Integer, String> map = new HashMap<>();

        final Integer targetKey = 0b1111_1111_1111_1111; // 65 535

        final String targetValue = "v";

        map.put(targetKey, targetValue);

        new Thread(() -> {

            //不断的put，然后扩容
            IntStream.range(0, targetKey).forEach(key -> map.put(key, "someValue"));

        }).start();

        while (true) {
            //如果线程安全，那么就算再扩容时，那么数据还是v，如果出现其他的val，则说明线程不安全
            String expectVal = map.get(targetKey);
            System.out.println(expectVal);
            if (null == expectVal) {

                throw new RuntimeException("HashMap is not thread safe.");

            }

        }

    }

}