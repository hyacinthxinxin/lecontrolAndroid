package com.cs1119it.fanxin.lecontrol.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.cs1119it.fanxin.lecontrol.DeviceActivity;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fanxin on 2017/3/25.
 */

public class SocketService extends Service implements ReceiveData {
    private Socket clientSocket = null;
    private boolean stop = true;
    private SocketBinder socketBinder = new SocketBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return socketBinder;
    }

    class SocketBinder extends Binder {
        public void startDownload() {
            Log.d("TAG", "startDownload() executed");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(this.getClass().getName(), "Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(this.getClass().getName(), "Start");
        new Thread() {
            @Override
            public void run() {
                SocketManager.sharedSocket().setListener(SocketService.this);
                super.run();
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);
    }

    @Override
    public void receiveData(String str) {
        Log.d(this.getClass().getName(), str);
//        Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
    }
}
