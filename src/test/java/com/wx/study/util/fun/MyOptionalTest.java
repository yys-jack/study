package com.wx.study.util.fun;

import org.junit.jupiter.api.Test;

import java.util.Optional;

class MyOptionalTest {
    @Test
    public void test() {
        Integer integer = 200;

        // ifPresent - 值存在时执行操作
        Optional.of(integer).ifPresent(System.out::println);

        // filter + isPresent - 检查过滤后是否存在
        boolean present = Optional.of(integer).filter(i -> i > 2000).isPresent();
        System.out.println("filter result present: " + present);

        // orElse - 提供默认值
        System.out.println("orElse: " + Optional.of(integer).filter(i -> i > 2000).orElse(100));

        // orElseGet - 延迟提供默认值
        System.out.println("orElseGet: " + Optional.of(integer).filter(i -> i > 2000).orElseGet(() -> 300));

        // isPresent - 检查值是否存在
        System.out.println("isPresent: " + Optional.of(integer).isPresent());

        // map - 转换值
        Integer result = Optional.of(integer).map(i -> i - 100).get();
        System.out.println("map result: " + result);
    }
}