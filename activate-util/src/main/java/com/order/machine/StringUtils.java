package com.order.machine;

public class StringUtils {

    public static String completeFixCode(String original, int fixed){
        StringBuilder sb = new StringBuilder();
        if (original.length() >= fixed){
            return original;
        }
        int i = fixed - original.length();
        while (i>0){
            sb.append("0");
            i--;
        }
        sb.append(original);
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(completeFixCode("1",3));
    }
}
