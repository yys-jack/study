package com.wx.study.object;

import org.openjdk.jol.info.ClassLayout;

/**
 * java对象头  64位虚拟机    根据虚拟机决定位数决定
 * <p>
 * mark word (64bit) + klass pointer/Class MetaData address(32bit/64bit(无压缩) = 12Byte
 * <p>
 * 对象头mark word   8byte 64bits +4byte  类信息
 * <p>
 * 实例变量
 * 对其填充（8 Bytes  倍数）
 *
 * @author wxli
 * @date 2021/9/9 20:33
 */
public class Test {
    public static void main(String[] args) {
        JavaObjectHead head = new JavaObjectHead();

        System.out.println(head.hashCode());//大端小端存储
        System.out.println(Integer.toHexString(head.hashCode()));//大端小端存储

        System.out.println(ClassLayout.parseInstance(head).toPrintable());
        synchronized (head) {
            System.out.println("加锁");
            System.out.println(ClassLayout.parseInstance(head).toPrintable());
        }
        int a = 1;
        System.out.println(ClassLayout.parseInstance(a).toPrintable());

        int[] b = new int[]{1,3};
        System.out.println(ClassLayout.parseInstance(b).toPrintable());


    }
}

class JavaObjectHead {
    int flag = 1;
    int[] mmm = new int[3];
    String a = "azxhckjsahd";

}
