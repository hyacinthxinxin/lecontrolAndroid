package com.cs1119it.fanxin.lecontrol;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import com.cs1119it.fanxin.lecontrol.service.SocketService;
import com.cs1119it.fanxin.lecontrol.unit.PollingUtils;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by fanxin on 2017/3/22.
 */

public class MyApplication extends Application {
    private static Context context;


    //返回
    public static Context getContextObject(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取Context
        context = getApplicationContext();
        startSocketService();
    }

    public void startSocketService() {
        Intent intent_socketService = new Intent(this, SocketService.class);
        startService(intent_socketService);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
