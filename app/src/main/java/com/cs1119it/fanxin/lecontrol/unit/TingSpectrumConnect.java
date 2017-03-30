package com.cs1119it.fanxin.lecontrol.unit;

/**
 * Created by fanxin on 2017/3/29.
 */

public class TingSpectrumConnect {
    private static String accessToken;
    private static String client;
    private static String uid;
    private static String tokenType;
    private static String user_id;
    private static Integer build_id;


    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        TingSpectrumConnect.accessToken = accessToken;
    }

    public static String getClient() {
        return client;
    }

    public static void setClient(String client) {
        TingSpectrumConnect.client = client;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        TingSpectrumConnect.uid = uid;
    }

    public static String getTokenType() {
        return tokenType;
    }

    public static void setTokenType(String tokenType) {
        TingSpectrumConnect.tokenType = tokenType;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        TingSpectrumConnect.user_id = user_id;
    }

    public static Integer getBuild_id() {
        return build_id;
    }

    public static void setBuild_id(Integer build_id) {
        TingSpectrumConnect.build_id = build_id;
    }
}
