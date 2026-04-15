package com.wx.study.threadlocal;

public class ThreadLocalDemo07 {

    public static void main(String[] args) {


        new Thread(() -> {
            new Service1().service1("你好");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            User user = UserContextHolder.holder.get();
            System.out.println(Thread.currentThread().getName() + user);
        }).start();
        new Thread(() -> {
            new Service1().service1("小李");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            User user = UserContextHolder.holder.get();
            System.out.println(Thread.currentThread().getName() + user);

        }).start();

    }

}

class Service1 {

    public void service1(String name) {

        User user = new User(name);

        UserContextHolder.holder.set(user);

        new Service2().service2();

    }

}

class Service2 {

    public void service2() {

        User user = UserContextHolder.holder.get();

        System.out.println(Thread.currentThread().getName() + "Service2拿到用户名：" + user.name);

        new Service3().service3();

    }

}

class Service3 {

    public void service3() {

        User user = UserContextHolder.holder.get();

        System.out.println(Thread.currentThread().getName() + "Service3拿到用户名：" + user.name);

        UserContextHolder.holder.remove();

    }

}

class UserContextHolder {

    public static ThreadLocal<User> holder = new ThreadLocal<>();

}

class User {

    String name;

    public User(String name) {

        this.name = name;

    }

}