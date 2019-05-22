package com.order.machine;

/**
 * @author miou
 * @date 2019-05-22
 */
public class encryptTools {

    public static String getXORCode(String code, int key){
        byte[] bytes = code.getBytes();
        byte[] bytes1 = new byte[bytes.length];
        for (int i=0;i<bytes.length;i++){
            bytes1[i] = (byte)(bytes[i]^key);
        }
        String result = new String(bytes1);
        return result;
    }

    public static void main(String[] args) {
        String a = getXORCode("abcdef",51);
        System.out.println(a);
        String b = getXORCode(a,51);
        System.out.println(b);
    }
}
