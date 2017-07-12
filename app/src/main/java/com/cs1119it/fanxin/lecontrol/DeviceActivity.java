package com.cs1119it.fanxin.lecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.adpter.DeviceAdapter;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.*;

public class DeviceActivity extends AppCompatActivity {
    List<Device> devices;
    List<Cam> cams;
    Integer deviceGroupType;
    MessageBroadCastReceiver messageBroadCastReceiver;
    DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        getIntentValues();
        signBroadCast();
        initData();
        initView();
        if (SocketManager.sharedSocket().isMinaConnected) {
            sendReadingStatusCode();
        }
    }

    private void setupToolBar(String title) {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.device_app_bar_layout);
        Toolbar toolbar = (Toolbar) appBarLayout.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView customTitleTextView = (TextView) toolbar.findViewById(R.id.custom_title_textView);
        customTitleTextView.setText(title);
        setTitle("");
    }

    private void getIntentValues() {
        Intent intent = getIntent();
        setupToolBar(intent.getStringExtra("DeviceGroupName"));
        deviceGroupType = intent.getIntExtra("DeviceGroupType", 0);
    }

    private void initData() {
        devices = SocketManager.sharedSocket().getDataModel().getDevicesByDeviceGroupType(this.deviceGroupType);
        cams = getAllCams();
        for (Cam cam : cams) {
            Log.d("TAG", cam.getName());
        }
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.device_recycler_view);
        if (deviceGroupType == 0) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(DeviceActivity.this, 2, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DeviceActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
        }
        deviceAdapter = new DeviceAdapter(devices);
        recyclerView.setAdapter(deviceAdapter);
    }

    private void sendReadingStatusCode() {
        String string = "";
        for (Cam cam : cams) {
            if (!cam.getStatusAddress().equals("0/0/0")) {
                LeControlCode leControlCode = new LeControlCode(cam.getStatusAddress(), cam.getControlType(), 0);
                string = string.concat(leControlCode.message(false));
            }
        }
        SocketManager.sharedSocket().sendMsg(string);
    }

    class MessageBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String address = intent.getStringExtra("Address");
            Integer value = intent.getIntExtra("Value", 0);
            Log.d(this.getClass().getName(), "address:" + address + "value:" + value);
            List<Cam> statusCams = getAllCamsByStatusAddress(address);
            if (statusCams.size() > 0) {
                if (statusCams.size() == 1) {
                    Cam cam = statusCams.get(0);
                    cam.setControlValue(value);
                } else {
                    for (Cam subCam : statusCams) {
                        if (subCam.getControlValue().equals(value)) {
                            subCam.setChecked(true);
                        } else {
                            subCam.setChecked(false);
                        }
                    }

                }
                deviceAdapter.notifyDataSetChanged();
            }
        }
    }

    private void signBroadCast() {
        messageBroadCastReceiver = new MessageBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("broadcast.action.GetMessage");
        intentFilter.setPriority(1000);
        registerReceiver(messageBroadCastReceiver, intentFilter);
    }

    private List<Cam> getAllCams() {
        List<Cam> cams = new ArrayList<>();
        for (Device device : devices) {
            for (Cam cam : device.getCams()) {
                cams.add(cam);
            }
        }
        return cams;
    }

    private List<Cam> getAllCamsByStatusAddress(String statusAddress) {
        List<Cam> statusCams = new ArrayList<>();
        for (Cam cam : cams) {
            if (cam.getStatusAddress().equals(statusAddress))
                statusCams.add(cam);
        }
        return statusCams;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageBroadCastReceiver);
    }

}
