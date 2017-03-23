package com.cs1119it.fanxin.lecontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cs1119it.fanxin.lecontrol.adpter.DeviceAdapter;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceActivity extends AppCompatActivity {
    List<Device> devices;
    Integer deviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

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

        Intent intent = getIntent();
        String deviceTypeName = intent.getStringExtra("DeviceTypeName");
        setTitle(deviceTypeName);

        deviceType = intent.getIntExtra("DeviceType", 0);

        initData();
        initView();

    }

    private void initData(){
        devices = new ArrayList<>();
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.device_recycler_view);
        if (deviceType == 0) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(DeviceActivity.this, 2, GridLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            for(int i=0; i<10; i++) {
                Device device = new Device("场景"+String.valueOf(i), "ic");
                device.setiType(0);
                List<Cam> cams = new ArrayList<>();

                Cam cam = new Cam("启动", "iii");
                cams.add(cam);
                device.setCams(cams);

                devices.add(device);
            }
        } else {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DeviceActivity.this, LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            for(int i=0; i<5; i++) {
                Device device = new Device("灯光"+String.valueOf(i), "ic");
                device.setiType(1);
                List<Cam> cams = new ArrayList<>();

                Cam cam = new Cam("开关", "iii");
                cams.add(cam);
                device.setCams(cams);

                devices.add(device);
            }
            for(int i=0; i<5; i++) {
                Device device = new Device("灯光"+String.valueOf(i), "ic");
                device.setiType(3);
                List<Cam> cams = new ArrayList<>();

                Cam cam = new Cam("开关", "iii");
                cams.add(cam);
                device.setCams(cams);

                devices.add(device);
            }
        }

        DeviceAdapter deviceAdapter = new DeviceAdapter(devices);
        recyclerView.setAdapter(deviceAdapter);
    }
}
