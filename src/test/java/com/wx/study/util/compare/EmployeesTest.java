package com.wx.study.compare;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class EmployeesTest {

    @Test
    void compareTo() {

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Employees> employees = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Employees employee = new Employees();
            employee.setName(scanner.nextLine());
            employee.setRank(scanner.nextInt());
            employee.setAgeLimit(scanner.nextInt());
            employee.setSalary(scanner.nextDouble());
            employees.add(employee);
        }
        System.out.println(employees);
    }
}