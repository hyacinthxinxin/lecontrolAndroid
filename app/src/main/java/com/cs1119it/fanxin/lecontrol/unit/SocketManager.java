package com.cs1119it.fanxin.lecontrol.unit;

import android.util.Log;

import com.cs1119it.fanxin.lecontrol.mina.ByteArrayCodecFactory;
import com.cs1119it.fanxin.lecontrol.mina.IoListener;
import com.cs1119it.fanxin.lecontrol.mina.MinaClientHandler;
import com.cs1119it.fanxin.lecontrol.model.DataModel;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * Created by fanxin on 2017/3/23.
 */

public class SocketManager {
    private DataModel dataModel;
    private IoSession session;
    private boolean needRefresh = false;
    private String socket_address;
    private int socket_port;

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
        socket_address = "192.168.100.4";
//        socket_address = dataModel.getBuilding().getSocketAddress();
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
        session.write(str);
    }

    private class MinaThread extends Thread {
        @Override
        public void run() {
            super.run();
            Log.e("MinaThread", "客户端链接开始...");
            final IoConnector connector = new NioSocketConnector();
            connector.setConnectTimeoutMillis(10000);
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ByteArrayCodecFactory()));
            connector.setHandler(new MinaClientHandler());
            connector.setDefaultRemoteAddress(new InetSocketAddress(socket_address, socket_port));
            connector.addListener(new IoListener() {
                @Override
                public void sessionDestroyed(IoSession ioSession) throws Exception {
                    try {
                        int failCount = 0;
                        while (true) {
                            Thread.sleep(5000);
                            ConnectFuture future = connector.connect();
                            future.awaitUninterruptibly();// 等待连接创建完成
                            session = future.getSession();// 获得session
                            if (session != null && session.isConnected()) {
                                Log.e("MinaThread", "客户端链接成功");
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            //开始连接
            try {
                ConnectFuture future = connector.connect();
                future.awaitUninterruptibly();
                session = future.getSession();
            } catch (Exception e) {
                Log.e("MinaThread", "客户端链接异常...");
            }
            if (session != null && session.isConnected()) {
                session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
                Log.e("MinaThread", "客户端链接断开...");
                // 彻底释放Session,
                // 退出程序时调用不需要重连的可以调用这句话，也就是短连接不需要重连。
                // 长连接不要调用这句话，注释掉就OK。
                // connector.dispose();
            }
        }
    }
}
