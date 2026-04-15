package com.wx.study.behavioralmode.Iterator;

import java.util.ArrayList;

/**
 * @author wxli
 * @date 2021/7/15 21:26
 */
public class PancakeHouseMenu {
    ArrayList menuItems;

    public PancakeHouseMenu() {
        menuItems = new ArrayList();
        addItem("K&B's pancake breakfast",
                "pancakes with scrambled eggs, and toast",
                true,
                2.99);
    }
    public void addItem(String name,String description,
                        boolean vegetarian,double price){
        MenuItem menuItem= new MenuItem(name, description, vegetarian, price);
        menuItems.add(menuItem);
    }
    public ArrayList getMenuItems(){
        return menuItems;
    }
}
