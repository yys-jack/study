package com.wx.study.algorithm.huawei.0325;
import java.util.*;

public class P1 {

    /**
     * 斗地主起源于湖北十堰房县，据传是一位叫吴修全的年轻人根据当地流行的扑克玩法“跑得快”改编的，如今已风靡整个中国，并流行于互联网上。
     * 牌型:
     * 单顺, 又称顺子，最少5张牌，最多12张牌（3⋯A），不能有2，也不能有大小王，不计花色
     * 例如：3-4-5-6-7-8，7-8-9-10-J-Q，3-4-5-6-7-8-9-10-J-Q-K-A
     * 可用的牌 3<4<5<6<7<8<9<10<J<Q<K<A<2 < B(小王)< C(大王)，每种牌除大小王外有4种花色(共有 13X4 + 2 张牌)
     * 输入1. 手上已有的牌 2. 已经出过的牌（包括对手出的和自己出的牌）
     * 输出: 对手可能构成的最长的顺子(如果有相同长度的顺子, 输出牌面最大的那一个)，如果无法构成顺子, 则输出 NO-CHAIN
     *
     * 输入描述:
     * 输入的第一行为当前手中的牌
     * 输入的第二行为已经出过的牌
     * 输出描述:
     * 最长的顺子
     * 示例1
     * 输入
     * 3-3-3-3-4-4-5-5-6-7-8-9-10-J-Q-K-A
     * 4-5-6-7-8-8-8
     * 输出
     * 9-10-J-Q-K-A
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String line1 = scanner.nextLine();

            String[] split = (line + "-" + line1).split("-");
            Poker poker = new Poker();
            for (String s : split) {
                poker.removeCard(s);
            }
            poker.computeLongestChain();

            char[] chars = "34567890JQKA".substring(poker.start, poker.end).toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char aChar : chars) {
                sb.append(aChar).append("-");
            }
            if (poker.haveChain) {
                System.out.println(sb.deleteCharAt(sb.length() - 1).toString().replaceAll("0", "10"));
            } else {
                System.out.println("NO-CHAIN");
            }
        }
    }

    static class Poker {

        private LinkedHashMap<String, Integer> cards = new LinkedHashMap<>();
        int start;
        int end;
        int len;
        boolean haveChain;

        public Poker() {
            init();
        }

        private void init() {
            cards.put("3", 4);
            cards.put("4", 4);
            cards.put("5", 4);
            cards.put("6", 4);
            cards.put("7", 4);
            cards.put("8", 4);
            cards.put("9", 4);
            cards.put("10", 4);
            cards.put("J", 4);
            cards.put("Q", 4);
            cards.put("K", 4);
            cards.put("A", 4);
            cards.put("2", 4);
            cards.put("B", 4);
            cards.put("C", 4);
        }

        public void removeCard(String card) {
            cards.computeIfPresent(card, (c , n) -> n > 0 ? --n : 0);
        }

        public void computeLongestChain() {
            int[] existItem = new int[13];
            int i = 0;
            for (Map.Entry<String, Integer> entry : cards.entrySet()) {
                if (entry.getKey().equals("2")) {
                    break;
                }
                existItem[i++] = entry.getValue() > 0 ? 1 : 0;
            }
            boolean findFirst = false;
            int start = 0;
            int end = 0;
            int len = 0;
            for (int j = 0; j < existItem.length; j++) {
                if (existItem[j] == 1 && !findFirst) {
                    start = j;
                    findFirst = true;
                    len++;
                } else if (existItem[j] == 1 && findFirst) {
                    len++;
                } else if (existItem[j] == 0 && findFirst) {
                    if(len >= 5) {
                        end = j;
                        storeMax(start, end, len);
                        haveChain = true;
                        len = 0;
                    }
                    findFirst = false;
                }
            }
        }

        private void storeMax(int start, int end, int len) {
            if(len > this.len) {
                this.start = start;
                this.end = end;
                this.len = len;
            } else if(len == this.len && start > this.start) {
                this.start = start;
                this.end = end;
                this.len = len;
            }
        }
    }
}
