package com.cs1119it.fanxin.lecontrol.mina;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by fanxin on 2017/4/4.
 */

public class IoListener implements IoServiceListener {
    @Override
    public void serviceActivated(IoService arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void serviceDeactivated(IoService arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void serviceIdle(IoService arg0, IdleStatus arg1) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionClosed(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionCreated(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionDestroyed(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub

    }
}