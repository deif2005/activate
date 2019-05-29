package com.order.machine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

}
