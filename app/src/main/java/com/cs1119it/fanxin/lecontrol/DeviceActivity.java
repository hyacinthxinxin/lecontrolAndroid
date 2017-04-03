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
import android.widget.Toast;

import com.cs1119it.fanxin.lecontrol.adpter.DeviceAdapter;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.service.ReceiveData;
import com.cs1119it.fanxin.lecontrol.unit.ByteStringUtil;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

public class DeviceActivity extends AppCompatActivity {
    List<Device> devices;
    Integer deviceGroupType;
    MessageBroadCastReceiver messageBroadCastReceiver;
    DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        setupToolBar();
        getIntentValues();
        signBroadCast();
        initData();
        initView();
        sendReadingStatusCode();
    }

    private void setupToolBar() {
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
    }

    private void getIntentValues() {
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("DeviceGroupName"));
        deviceGroupType = intent.getIntExtra("DeviceGroupType", 0);
    }

    private void initData() {
        devices = SocketManager.sharedSocket().getDevicesByDeviceGroupType(this.deviceGroupType);
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
        for (Cam cam : getAllCams()) {
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
            for (Device device : devices) {
                switch (device.getiType()) {
                    case 0:
                    case 3:
                        break;
                    case 1:
                    case 2:
                    case 5:
                        for (Cam cam : device.getCams()) {
                            if (cam.getStatusAddress().equals(address)) {
                                cam.setControlValue(value);
                            }
                        }
                        break;
                    case 4:
                        List<Cam> airConditioningSpeedCams = device.getCamsIn(Cam.airConditioningSpeedCamTypes);
                        List<Cam> airConditioningModeCams = device.getCamsIn(Cam.airConditioningModeCamTypes);
                        for (Cam cam : device.getCams()) {
                            if (asList(Cam.singleCamTypes).contains(cam.getiType())) {
                                if (cam.getStatusAddress().equals(address)) {
                                    cam.setControlValue(value);
                                }
                            }
                        }
                        if (airConditioningSpeedCams.size() > 0) {
                            for (Cam cam : airConditioningSpeedCams) {
                                if (cam.getStatusAddress().equals(address) && cam.getControlValue().equals(value)) {
                                    cam.setChecked(true);
                                } else {
                                    cam.setChecked(false);
                                }
                            }
                        }
                        if (airConditioningModeCams.size() > 0) {
                            for (Cam cam : airConditioningModeCams) {
                                if (cam.getStatusAddress().equals(address) && cam.getControlValue().equals(value)) {
                                    cam.setChecked(true);
                                } else {
                                    cam.setChecked(false);
                                }
                            }
                        }
                        break;
                    case 6:
                        List<Cam> freshAirSpeedCams = device.getCamsIn(Cam.freshAirSpeedCamTypes);
                        for (Cam cam : device.getCams()) {
                            if (asList(Cam.singleCamTypes).contains(cam.getiType())) {
                                if (cam.getStatusAddress().equals(address)) {
                                    cam.setControlValue(value);
                                }
                            }
                        }
                        if (freshAirSpeedCams.size() > 0) {
                            for (Cam cam : freshAirSpeedCams) {
                                if (cam.getStatusAddress().equals(address) && cam.getControlValue().equals(value)) {
                                    cam.setChecked(true);
                                } else {
                                    cam.setChecked(false);
                                }
                            }
                        }
                        break;
                    default:break;
                }
            }
            deviceAdapter.notifyDataSetChanged();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageBroadCastReceiver);
    }

}
