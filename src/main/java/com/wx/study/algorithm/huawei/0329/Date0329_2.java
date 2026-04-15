package com.wx.study.algorithm.huawei.0329;
package com.lyao.date;

import java.util.*;


/**
 * List<String>
 * <p>
 * Map<String>
 */
public class Date0401_2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        System.out.println(func(input));
    }

    /**
     * 输入
     * xyxyXX
     * <p>
     * 输出
     * x:2;y:2;X:2;
     */
    private static String func(String input) {
//        List<Result> list = new ArrayList<>();
//        for (int i = 0; i < input.length(); i++) {
//            char ch = input.charAt(i);
//            addChToList(list, ch);
//        }
//
//        list.sort(new Comparator<Result>() {
//            @Override
//            public int compare(Result o1, Result o2) {
//                if (o1.count == o2.count) {
//                    if ((isLowerLetter(o1.ch) && isLowerLetter(o2.ch))
//                            || (isUpperLetter(o1.ch) && isUpperLetter(o2.ch)){
//                        return o1.ch.compareTo(o2.ch);
//                    }
//                    return String.valueOf(o1.ch).compareTo(String.valueOf(o2.ch));
//                }
//                return o2.count - o1.count;
//            }
//        });
//
//        StringBuffer sb = new StringBuffer();
//        for (Result result : list) {
//            sb.append(result.ch).append(":").append(result.count).append(";");
//        }


        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            addChToMap(map, ch);
        }

        List<Map.Entry<Character, Integer>> listData = new ArrayList<>(map.entrySet());
        listData.sort(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                if (o1.getValue() == o2.getValue()){
                    if ((isLowerLetter(o1.getKey()) && isLowerLetter(o2.getKey()))
                            || (isUpperLetter(o1.getKey()) && isUpperLetter(o2.getKey()))){
                        return o1.getKey().compareTo(o2.getKey());
                    }
                    return o2.getKey() - o1.getKey();
                }
                return o2.getValue() - o1.getValue();
            }
        });

        StringBuffer sb2 = new StringBuffer();
        for (Map.Entry<Character, Integer> result : listData) {
            sb2.append(result.getKey()).append(":").append(result.getValue()).append(";");
        }
        return sb2.toString();
    }

    private static boolean isLowerLetter(char ch) {
        if (ch <= 'z' && ch >= 'a') {
            return true;
        }
        return false;
    }

    private static boolean isUpperLetter(char ch) {
        if (ch <= 'Z' && ch >= 'A') {
            return true;
        }
        return false;
    }

    private static void addChToMap(Map<Character, Integer> map, char ch) {
        if (map.containsKey(ch)) {
            Integer temp = map.get(ch);
            temp++;
            map.put(ch, temp);
        } else {
            map.put(ch, 1);
        }
    }

    private static void addChToList(List<Result> list, char ch) {
        boolean isFind = false;
        for (Result result : list) {
            if (result.ch == ch) {
                result.count++;
                isFind = true;
                break;
            }
        }
        if (!isFind) {
            list.add(new Result(ch));
        }
    }


    static class Result {
        char ch;
        int count;

        public Result(char ch) {
            this.count = 1;
            this.ch = ch;
        }
    }


}
