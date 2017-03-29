package com.cs1119it.fanxin.lecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cs1119it.fanxin.lecontrol.adpter.DeviceAdapter;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.service.ReceiveData;
import com.cs1119it.fanxin.lecontrol.unit.ByteStringUtil;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DeviceActivity extends AppCompatActivity  {
    List<Device> devices;
    Integer deviceGroupType;
    MessageBroadCastReceiver messageBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        setupToolBar();
        getIntentValues();
        signBroadCast();
        initData();
        initView();
    }

    private void setupToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.device_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.logo);
        toolbar.setNavigationIcon(R.mipmap.arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getIntentValues() {
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("DeviceGroupName"));
        deviceGroupType = intent.getIntExtra("DeviceGroupType", 0);
    }

    private void initData(){
        devices = SocketManager.sharedSocket().getDevicesByDeviceGroupType(this.deviceGroupType);
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.device_recycler_view);
        if (deviceGroupType == 0) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(DeviceActivity.this, 2, GridLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DeviceActivity.this, LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
        }
        DeviceAdapter deviceAdapter = new DeviceAdapter(devices);
        recyclerView.setAdapter(deviceAdapter);
    }

    class MessageBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String address = intent.getStringExtra("Address");
            Integer value = intent.getIntExtra("Value", 0);
            Log.d(this.getClass().getName(), "address:" + address + "value:" + value);
        }
    }

    private void signBroadCast() {
        messageBroadCastReceiver = new MessageBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("broadcast.action.GetMessage");
        intentFilter.setPriority(1000);
        registerReceiver(messageBroadCastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageBroadCastReceiver);
    }

}
