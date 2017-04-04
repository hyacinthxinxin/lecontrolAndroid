package com.cs1119it.fanxin.lecontrol.mina;

import android.content.Intent;
import android.util.Log;

import com.cs1119it.fanxin.lecontrol.MyApplication;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by fanxin on 2017/4/4.
 */

public class MinaClientHandler extends IoHandlerAdapter {
    private String Tag = "MinaClientHandler";

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        Log.e(Tag, "messageReceived:" + message.toString());

        for (String string : message.toString().split("7A")) {
            if (string.length() > 10) {
                Integer first_address = Integer.parseInt(string.substring(2, 3), 16);
                Integer second_address = Integer.parseInt(string.substring(3, 4), 16);
                Integer third_address = Integer.parseInt(string.substring(4, 6), 16);
                String address = first_address + "/" + second_address + "/" + third_address;
                Integer value = Integer.parseInt(string.substring(8, 10), 16);

                Intent intent = new Intent("broadcast.action.GetMessage");
                intent.putExtra("Address", address);
                intent.putExtra("Value", value);

                MyApplication.getContextObject().sendOrderedBroadcast(intent, null);
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        super.inputClosed(session);
    }

}
