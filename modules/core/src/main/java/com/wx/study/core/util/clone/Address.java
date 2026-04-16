package com.wx.study.core.util.clone;

public class Address implements Cloneable {
    private String name;

    public Address(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Address clone() {
        try {
            return (Address) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
