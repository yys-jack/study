package com.wx.study.core.jvm.memoryoverflow;

/**
 * VM Args：-Xss128k
 *
 * @author wxli
 * @date 2021/9/10 16:42
 */
public class StackSOF {
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        StackSOF oom=new StackSOF();
        try {
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println(oom.stackLength);
            throw e;
        }
    }
}
