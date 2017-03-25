package com.cs1119it.fanxin.lecontrol.unit;

/**
 * Created by fanxin on 2017/3/25.
 */

public class ToHex {
    public static String getHex(int i) {
        float temp=255*i*1.0f/100f;
        int round = Math.round(temp);//四舍五入
        String hexString = Integer.toHexString(round);
        if (hexString.length()<2) {
            hexString+="0";
        }
        return hexString.toUpperCase();
    }
}
