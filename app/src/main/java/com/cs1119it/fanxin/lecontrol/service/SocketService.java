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
    public static final String ACTION = "com.fanxin.lecontrol.service.SocketService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread() {
            @Override
            public void run() {
            }
        }.start();
        SocketManager.sharedSocket().reConnect();
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendMessage(String str) {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = str;
        handler.sendMessage(msg);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(this.getClass().getName(), msg.toString());
        }
    };
}
