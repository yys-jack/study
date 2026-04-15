package com.wx.study.niuke.hwod;

import java.util.*;

public class T78 {
    /*
  运维工程师采集到某产品线网运行一天产生的日志n条
  现需根据日志时间先后顺序对日志进行排序
  日志时间格式为H:M:S.N
  H表示小时(0~23)
  M表示分钟(0~59)
  S表示秒(0~59)
  N表示毫秒(0~999)
  时间可能并没有补全
  也就是说
  01:01:01.001也可能表示为1:1:1.1

  输入描述
     第一行输入一个整数n表示日志条数
     1<=n<=100000
     接下来n行输入n个时间

   输出描述
     按时间升序排序之后的时间
     如果有两个时间表示的时间相同
     则保持输入顺序

   示例：
     输入：
      2
      01:41:8.9
      1:1:09.211
     输出
       1:1:09.211
       01:41:8.9
   示例
      输入
       3
       23:41:08.023
       1:1:09.211
       08:01:22.0
      输出
        1:1:09.211
        08:01:22.0
        23:41:08.023

    示例
      输入
        2
        22:41:08.023
        22:41:08.23
      输出
        22:41:08.023
        22:41:08.23
      时间相同保持输入顺序
       */
    public static void main(String[] args) {
        //输入
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] strs = new String[n];
        for (int i = 0; i < n; i++) {
            strs[i] = scanner.next();
        }
        List<String> toSort = new ArrayList<>(Arrays.asList(strs));
        toSort.sort(Comparator.comparingLong(T78::getTime));
        for (String str : toSort) {
            System.out.println(str);
        }
    }

    public static long getTime(String str) {
        String[] t1 = str.split(":");
        String[] t2 = t1[2].split("\\.");
        long h = Long.parseLong(t1[0]) * 60 * 60 * 1000;
        long m = Long.parseLong(t1[1]) * 60 * 1000;
        long s = Long.parseLong(t2[0]) * 1000;
        long n = Long.parseLong(t2[1]);
        return h + m + s + n;
    }

    public void fun(){
        //输入
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] strs = new String[n];
        for (int i = 0; i < n; i++) {
            strs[i] = scanner.next();
        }
        List<String> toSort = new ArrayList<>(Arrays.asList(strs));
        toSort.sort((o1, o2) -> {
            //将时间转成毫秒秒来计算
            String[] split1 = o1.split(":");
            String[] split2 = o2.split(":");
            double s1 = Double.parseDouble(split1[0]) * 60 * 60 * 1000
                    + Double.parseDouble(split1[1]) * 60 * 1000
                    + Double.parseDouble(split1[2].split("\\.")[0]) * 1000
                    + Double.parseDouble(split1[2].split("\\.")[1]);
            double s2 = Double.parseDouble(split2[0]) * 60 * 60 * 1000
                    + Double.parseDouble(split2[1]) * 60 * 1000
                    + Double.parseDouble(split2[2].split("\\.")[0]) * 1000
                    + Double.parseDouble(split2[2].split("\\.")[1]);
            if (s1 < s2) {
                return -1;
            } else if (s1 > s2) {
                return 1;
            }
            return 0;
        });
        for (String str : toSort) {
            System.out.println(str);
        }
    }
}
