package com.wx.study.behavioralmode.Iterator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对象村餐厅
 * @author wxli
 * @date 2021/7/15 21:24
 */
@AllArgsConstructor
@Getter
public class MenuItem {
    String name;
    String description;
    boolean vegetarian;
    double price;

}
