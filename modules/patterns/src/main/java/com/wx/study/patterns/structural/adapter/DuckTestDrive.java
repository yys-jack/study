package com.wx.study.patterns.structural.adapter;

/**
 * @author wxli
 * @date 2021/7/8 22:46
 */
public class DuckTestDrive {
    public static void main(String[] args) {
        MallardDuck mallardDuck = new MallardDuck();

        WildTurkey wildTurkey = new WildTurkey();

        Duck turkeyAdapter = new TurkeyAdapter(wildTurkey);

        wildTurkey.gobble();
        wildTurkey.fly();

        System.out.println("\n mallardDuck,绿头鸭");
        testDuck(mallardDuck);

        System.out.println("\n turkeyAdapter，火鸡伪装鸭子");
        testDuck(turkeyAdapter);
    }

    private static void testDuck(Duck duck) {
        duck.quack();
        duck.fly();
    }

}
