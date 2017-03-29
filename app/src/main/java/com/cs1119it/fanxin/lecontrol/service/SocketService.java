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
                try {
                    if(!SocketManager.sharedSocket().setListener(SocketService.this))
                        Log.d(this.getName(), "Socket not Connect");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);
    }

    @Override
    public void receiveData(final String str) {
        new Thread(){
            @Override
            public void run() {
                for (String message: str.split("7A")) {
                    if (message.length() > 10 ) {
                        Integer first_address = Integer.parseInt(message.substring(2, 3), 16);
                        Integer second_address = Integer.parseInt(message.substring(3, 4), 16);
                        Integer third_address = Integer.parseInt(message.substring(4, 6), 16);
                        String address = first_address + "/" + second_address + "/" + third_address;
                        Integer value = Integer.parseInt(message.substring(8, 10), 16);

                        Intent intent = new Intent("broadcast.action.GetMessage");
                        intent.putExtra("Address", address);
                        intent.putExtra("Value", value);
                        sendOrderedBroadcast(intent, null);
                    }
                }
                super.run();
            }
        }.start();

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
