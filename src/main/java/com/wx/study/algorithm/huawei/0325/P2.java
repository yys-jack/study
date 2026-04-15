package com.wx.study.algorithm.huawei.0325;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class P2 {

    /**
     * 给定一个非空字符串S，其被N个‘-’分隔成N+1的子串，给定正整数K，要求除第一个子串外，其余的子串每K个字符组成新的子串，并用‘-’分隔。对于新组成
     * 的每一个子串，如果它含有的小写字母比大写字母多，则将这个子串的所有大写字母转换为小写字母；反之，如果它含有的大写字母比小写字母多，则将这个子
     * 串的所有小写字母转换为大写字母；大小写字母的数量相等时，不做转换。
     *
     * 输入描述:
     * 输入为两行，第一行为参数K，第二行为字符串S。
     * 输出描述:
     * 输出转换后的字符串
     *
     * 输入
     * 3
     * 12abc-abCABc-4aB@
     * 输出
     * 12abc-abc-ABC-4aB-@
     *
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            Integer num = Integer.valueOf(scanner.nextLine());
            String line = scanner.nextLine();

            // 1 按-切割
            String[] split = line.split("-");

            // 2 将第二个开始到结束子串按num且切割
            LinkedList<String> list = new LinkedList<>();
            for (int i = 1; i < split.length; i++) {
                List<String> splitArr = splitByNum(split[i], num);
                list.addAll(splitArr);
            }

            // 3 从第二个开始对所有字符串按规则进行大小写转换
            for (int i = 0; i < list.size(); i++) {
                list.add(i, convert(list.remove(i)));
            }
            // 4 重新拼接
            StringBuilder sb = new StringBuilder(split[0] + "-");
            list.forEach(s -> sb.append(s).append("-"));
            System.out.println(sb.deleteCharAt(sb.length() - 1));
        }
    }

    private static String convert(String s) {
        int low = 0;
        int up = 0;
        for (char c : s.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                low++;
            } else if (c >= 'A' && c <= 'Z') {
                up++;
            }
        }
        if(low > up) {
            return s.toLowerCase(Locale.ROOT);
        } else if (up > low) {
            return s.toUpperCase(Locale.ROOT);
        }
        return s;
    }

    private static List<String> splitByNum(String s, Integer num) {
        LinkedList<String> list = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c);
            if (sb.length() == num) {
                list.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        if(sb.length() > 0) {
            list.add(sb.toString());
        }
        return list;
    }
}
