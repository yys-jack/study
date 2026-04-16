package com.wx.study.concurrent.escape;

import java.util.HashSet;
import java.util.Set;

public class PublishObject {
    private static Set<String> knownStr;

    public void initialize() {
        knownStr = new HashSet<>();
    }
}

class UnsafeStates {
    private String[] states = new String[]{"AK", "AL"};    //可变对象

    public String[] getStates() {                         //是内部的可变状态逸出
        return states;
    }
}

class ThisEscape {

    public ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {        //当ThisEscape发布EventListener时，也隐含发布了ThisEscape实例本身
            public void onEvent(Event e) {
                doSomething(e);           //内部类实例包含了对thisEscape实例的隐含引用
            }
        });
    }

    void doSomething(Event e) {

    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }

    public static void main(String[] args) {

    }

}

class SafeListener {
    private final EventListener listener;

    private SafeListener() {
        this.listener = new EventListener() {
            @Override
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomething(Event e) {
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface EventSource {
        void registerListener(EventListener e);
    }

    interface Event {
    }
}
