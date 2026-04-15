package com.wx.study.reference;

public class ReferenceTypeAccessTest {
    public static void main(String[] args) {
        T li_si = new T("li si");
    }
}

class T {
    String name;

    public T(String name) {
        this.name = name;
    }
}
