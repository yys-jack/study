package com.wx.study.clone;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    void testClone() {
        Person person = new Person(new Address("武汉"));
        Person clone = person.clone();
        assertEquals(person.getAddress(), clone.getAddress());
    }
}