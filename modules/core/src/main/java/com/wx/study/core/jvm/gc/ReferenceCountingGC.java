package com.wx.study.core.jvm.gc;

/**
 * 引用算法
 *
 * @author wxli
 * @date 2021/9/13 11:36
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;
        //假设这行发生GC，objA和objB是否能被回收
        System.gc();
    }
}
