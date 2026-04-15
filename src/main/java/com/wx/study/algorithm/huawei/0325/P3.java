package com.wx.study.algorithm.huawei.0325;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P3 {

    /**
     * 某公司组织一场公开招聘活动，假设由于人数和场地的限制，每人每次面试的时长不等，并已经安排给定，用(S1,E1)、(S2,E2)、(Sj,Ej)...
     * (Si < Ei，均为非负整数)表示每场面试的开始和结束时间。面试采用一对一的方式，即一名面试官同时只能面试一名应试者，一名面试官完成一
     * 次面试后可以立即进行下一场面试，且每个面试官的面试人次不超过m。
     * 为了支撑招聘活动高效顺利进行，请你计算至少需要多少名面试官。
     *
     * 输入描述:
     * 输入的第一行为面试官的最多面试人次m，第二行为当天总的面试场次n，接下来的n行为每场面试的起始时间和结束时间，起始时间和结束时间用空格分隔。
     * 其中，1 <= n, m <= 500
     * 输出描述:
     * 输出一个整数，表示至少需要的面试官数量。
     * 示例1
     * 输入
     * 2
     * 5
     * 1 2
     * 2 3
     * 3 4
     * 4 5
     * 5 6
     * 输出
     * 3
     *
     * 说明:总共有5场面试，且面试时间都不重叠，但每个面试官最多只能面试2人次，所以需要3名面试官。
     *
     * 示例2
     * 输入
     * 3
     * 3
     * 1 2
     * 2 3
     * 3 4
     * 输出
     * 1
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            Integer max = Integer.valueOf(scanner.nextLine());
            Integer num = Integer.valueOf(scanner.nextLine());
            LinkedList<Interval> rounds = new LinkedList<>();
            for (Integer i = 0; i < num; i++) {
                String line = scanner.nextLine();
                String[] strings = line.split(" ");
                rounds.add(new Interval(Integer.valueOf(strings[0]), Integer.valueOf(strings[1])));
            }
            rounds.sort(Comparator.comparing(Interval::getStart));
            PriorityQueue<Interval> intervals = new PriorityQueue<>(rounds.size(),
                Comparator.comparing(Interval::getEnd));

            int limit = max - 1;
            int interviewerCount = 0;

            intervals.offer(rounds.get(0));
            for (int i = 1; i < rounds.size(); i++) {
                Interval cur = rounds.get(i);
                Interval poll = intervals.poll();
                if (poll.end <= cur.start && limit > 0) {
                    poll.end = cur.end;
                    intervals.offer(poll);
                    limit--;
                } else if(poll.end <= cur.start && limit <= 0) {
                    interviewerCount++;
                    intervals.offer(cur);
                    limit = max - 1;
                } else {
                    intervals.offer(cur);
                    intervals.offer(poll);
                }
            }
            System.out.println(intervals.size() + interviewerCount);
        }

    }

    static class Interval {
        Integer start;
        Integer end;

        public Interval(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        public Integer getStart() {
            return start;
        }

        public Integer getEnd() {
            return end;
        }
    }
}
