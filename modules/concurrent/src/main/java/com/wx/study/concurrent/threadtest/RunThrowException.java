package com.wx.study.concurrent.threadtest;

import java.io.IOException;

public class RunThrowException {

    /**
     * 普通方法内可以 throw 异常，并在方法签名上声明 throws
     */

    public void normalMethod() throws Exception {

        throw new IOException();

    }

    Runnable runnable = new Runnable() {

        /**
         *  run方法上无法声明 throws 异常，且run方法内无法 throw 出 checked Exception，除非使用try catch进行处理

         */

        @Override

        public void run() {


            try {
                throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    };

}