package com.cs1119it.fanxin.lecontrol.unit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.cs1119it.fanxin.lecontrol.MyApplication;
import com.cs1119it.fanxin.lecontrol.mina.ByteArrayCodecFactory;
import com.cs1119it.fanxin.lecontrol.mina.IoListener;
import com.cs1119it.fanxin.lecontrol.mina.MinaClientHandler;
import com.cs1119it.fanxin.lecontrol.model.DataModel;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * Created by fanxin on 2017/3/23.
 */

public class SocketManager {
    private DataModel dataModel;
    private IoSession leSession;
    private boolean needRefresh = false;
    private String socket_address;
    private int socket_port;
    public boolean isMinaConnected = false;

    public boolean isNeedRefresh() {
        return needRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    //#1
    private volatile static SocketManager socketManager;

    //#2
    private SocketManager() {
        dataModel = new DataModel();
        dataModel.setBuildingDetail();
        socket_address = dataModel.getBuilding().getSocketAddress();
        socket_port = dataModel.getBuilding().getSocketPort();
    }

    //#3
    public static SocketManager sharedSocket() {
        if (socketManager == null) {
            synchronized (SocketManager.class) {
                if (socketManager == null) {
                    socketManager = new SocketManager();
                }
            }
        }
        return socketManager;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void reConnect() {
        new MinaThread().start();
    }

    public void sendMsg(String str) {
        if (isMinaConnected) {
            leSession.write(str);
        }
    }

    private class MinaThread extends Thread {
        @Override
        public void run() {
            super.run();
//            Log.e("MinaThread", "客户端链接开始..." + socket_address + ":" + String.valueOf(socket_port));

            final IoConnector connector = new NioSocketConnector();
            connector.setConnectTimeoutMillis(10000);
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ByteArrayCodecFactory()));
            connector.setDefaultRemoteAddress(new InetSocketAddress(socket_address, socket_port));

            connector.setHandler(new MinaClientHandler() {
                @Override
                public void sessionClosed(IoSession session) throws Exception {
                    super.sessionClosed(session);
                    sendHandlerMessage("智能家居服务器连接断开");
                    isMinaConnected = false;
                    while (true) {
                        try {
                            Thread.sleep(5000);
                            ConnectFuture future = connector.connect();
                            future.awaitUninterruptibly();
                            leSession = future.getSession();
                            isMinaConnected = true;
                            sendHandlerMessage("智能家居服务器重连成功");
                            break;
                        } catch (Exception e) {
                            // TODO: 2017/4/5
                        }
                    }
                }
            });

            try {
                ConnectFuture future = connector.connect();
                future.awaitUninterruptibly();
                leSession = future.getSession();
                isMinaConnected = true;
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "智能家居服务器连接成功!");
                message.setData(bundle);
                handler.sendMessage(message);
            } catch (Exception e) {
                isMinaConnected = false;
                // TODO: 2017/4/5
                sendHandlerMessage("智能家居服务器连接失败");
                while (true) {
                    try {
                        Thread.sleep(5000);
                        ConnectFuture future = connector.connect();
                        future.awaitUninterruptibly();
                        leSession = future.getSession();
                        isMinaConnected = true;
                        sendHandlerMessage("智能家居服务器连接成功");
                        break;
                    } catch (Exception e1) {
                        // TODO: 2017/4/5
                    }
                }
            }
        }

        private void sendHandlerMessage(String string) {
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("msg", string);
            message.setData(bundle);
            handler.sendMessage(message);
        }

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String string = bundle.getString("msg");
                Toast.makeText(MyApplication.getContextObject(), string, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
