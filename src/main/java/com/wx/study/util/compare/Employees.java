package com.wx.study.compare;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employees implements Comparable<Employees> {
    private String name;
    private Integer rank;
    private Integer ageLimit;
    private Double salary;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(Employees o) {
        int i = rank.compareTo(o.rank);
        if (i == 0) {
            i = ageLimit.compareTo(o.ageLimit);
            if (i == 0) {
                return salary.compareTo(o.salary);
            }
        }
        return i;
    }
}
