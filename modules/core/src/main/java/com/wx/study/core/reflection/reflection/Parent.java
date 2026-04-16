package com.wx.study.core.reflection.reflection;

import com.wx.study.core.reflection.reflection.annotation.Idempotent;
import lombok.Setter;

/**
 * @author wxli
 * @date 2021/7/12 18:02
 */
@Idempotent
public class Parent {

    @Setter
    private String name;

    @Idempotent
    public void test(){

    }
}
