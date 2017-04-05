package com.cs1119it.fanxin.lecontrol.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by fanxin on 2017/3/25.
 */

public class SocketService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("Socket Service", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Socket Service", "onStartCommand");
        new Thread() {
            @Override
            public void run() {
            }
        }.start();
        SocketManager.sharedSocket().reConnect();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("Socket Service", "onDestroy");
        super.onDestroy();
    }
}
