package com.wx.study.algorithm.huawei.0322;
package train;

public class MaxFoot {
    public static void main(String[] args) {
        // String str1 = "(34)";
        // String[] leftArr = str1.split("\\(");
        // System.out.println(Arrays.toString(leftArr));
        // System.out.println(str1.indexOf(")"));
        //
        // String str = "333aaaa(100,200)f2r3rfasf(300,400)";
        // System.out.println(new MaxFoot().getMaxFoot(str));

        String str = "asfefaweawf(300,400)aw(01,1)fe((100,200))";
        System.out.println(new MaxFoot().getMaxFoot(str));
    }

    /**
     * 字符串截取操作
     * @param str
     * @return
     */
    public String getMaxFoot(String str) {
        String result = "(0,0)";
        //String[] leftArr = str.split("[\\(\\)]");  //是否默认使用了正则？

        String[] leftArr = str.split("\\(");
        int maxLen = 0;
        if (leftArr.length > 1) {
            for (int index = 1; index < leftArr.length; index++) {
                int leftIndex = leftArr[index].indexOf(")");
                if (leftIndex > 0) {
                    String centerStr = leftArr[index].substring(0, leftIndex);
                    String[] numStr = centerStr.split(",");
                    if (numStr.length == 2) {
                        //判断是否为非法坐标
                        if (!numStr[0].startsWith("0") && !numStr[1].startsWith("0")) {
                            int x = Integer.parseInt(numStr[0]);
                            int y = Integer.parseInt(numStr[1]);
                            if ((x * x + y * y > maxLen)) {
                                maxLen = x * x + y * y;
                                result = "(" + x + "," + y + ")";
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

}
