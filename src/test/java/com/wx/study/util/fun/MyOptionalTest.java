package com.wx.fun;

import org.junit.jupiter.api.Test;

class MyOptionalTest {
    @Test
    public void test() {
        Integer integer = 200;

        java.util.Optional.of(integer).ifPresent(System.out::println);

        System.out.println(java.util.Optional.of(integer).filter(i -> i > 2000).get());

        System.out.println(java.util.Optional.of(integer).filter(i -> i > 2000).orElse(100));

        System.out.println(java.util.Optional.of(integer).filter(i -> i > 2000).orElseThrow(RuntimeException::new));

        System.out.println(java.util.Optional.of(integer).filter(i -> i > 2000).orElseGet(() -> 300));

        System.out.println(java.util.Optional.of(integer).isPresent());

        java.util.Optional.of(integer).map(integer1 -> integer - 100).get();
    }
}