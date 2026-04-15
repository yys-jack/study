package com.wx.study.creationmode.factory.method;

/**
 * @author wxli
 * @date 2021/7/2 14:45
 */
public class ChineseFoodFactory implements FoodFactory{
    @Override
    public Food make(String type) {
        if (type.equals("huawei")){
            return new Food("huawei","500");
        }else if (type.equals("xiaomi")){
            return new Food("xiaomi","5000");
        }
        return null;
    }
}
