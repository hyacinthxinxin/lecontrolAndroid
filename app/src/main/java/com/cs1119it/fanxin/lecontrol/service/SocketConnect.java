package com.cs1119it.fanxin.lecontrol.service;

import android.os.Message;
import android.util.Log;

import com.cs1119it.fanxin.lecontrol.unit.ByteStringUtil;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static android.R.id.input;

/**
 * Created by fanxin on 2017/3/25.
 */

public class SocketConnect {
    private String socket_address;

    private int socket_port;

    private static Socket client = null;

    public SocketConnect(String socket_address, int socket_port) {
        this.socket_address = socket_address;
        this.socket_port = socket_port;
    }

    public void reConnect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    client = new Socket(socket_address, socket_port);
                    client.setSoTimeout(3000);
                    Log.e("JAVA", "建立连接：" + client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static boolean isConnect(){
        if (client==null){
            return false;
        }
        if(client.isClosed()) {
            return false;
        }
        if(client.isConnected()) {
            return true;
        }
        return false;
    }

    public boolean sendMsg(String str){
        try {
            if(client.isClosed()) {
                return false;
            }
            client.getOutputStream().write(ByteStringUtil.hexStrToByteArray(str));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
