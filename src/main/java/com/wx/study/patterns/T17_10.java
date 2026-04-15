package com.wx;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wxli
 * @date 2021/7/9 10:52
 */
public class T17_10 {
    public static int majorityElement(int[] nums) {
        int i = -1;
        List<Integer> ole = Arrays.stream(nums).boxed().collect(Collectors.toList());

        while (ole.size() > 0) {
            if (ole.size() == 1) {
                i = ole.get(0);
            }
            List<Integer> news = ole.stream()
                    .distinct().collect(Collectors.toList());
            for (Integer integer : news) {
                ole.remove(integer);
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int[] ints = {2, 2, 2, 3, 3, 4, 4};
        int i = majorityElement(ints);
        System.out.println(i);
    }
}
