package com.wx.study.leetcode.gettingstarted.tree;


/**
 * https://leetcode.cn/problems/validate-ip-address/
 */
public class T468 {
    public String validIPAddress(String queryIP) {
        return isIPv4(queryIP)?"IPv4":isIPv6(queryIP)?"IPv6":"Neither";
    }
    public boolean isIPv4(String s){
        //1、根据"."分割开；2、四段；3、每段0-255；4、无前导0；5、全是digit
        String t[]=s.split("\\.",-1);
        if(t.length!=4){return false;}
        for(int i=0;i<4;i++){
            if(t[i].length()==0||t[i].length()>3||t[i].length()>1&&t[i].charAt(0)=='0'){return false;}
            int sum=0;
            for(char c:t[i].toCharArray()){
                if(!Character.isDigit(c)){return false;}
                sum=sum*10+c-'0';
            }
            if(sum>255){return false;}
        }
        return true;
    }
    public boolean isIPv6(String s){
        //1、根据":"分隔开；2、八段；3、1-4位；4、字母(abcdef)或者数字
        s=s.toLowerCase();
        String t[]=s.split(":",-1);
        if(t.length!=8){return false;}
        for(int i=0;i<8;i++){
            if(t[i].length()==0||t[i].length()>4){return false;}
            for(char c:t[i].toCharArray()){if(!(c>='0'&&c<='9')&&!(c>='a'&&c<='f')){return false;}}
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new T468().validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334:"));
    }
}
