package com.cs1119it.fanxin.lecontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cs1119it.fanxin.lecontrol.adpter.AreaDetailAdapter;
import com.cs1119it.fanxin.lecontrol.adpter.FloorAndAreaAdapter;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.model.DeviceGroupType;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AreaDetailActivity extends AppCompatActivity {
    private Area area;
    private List<DeviceGroupType> deviceGroupTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.area_toolbar);
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
        Integer floorId = intent.getIntExtra("floorId", 0);
        Integer areaId = intent.getIntExtra("areaId", 0);
//        area = (Area) intent.getSerializableExtra("area");
        area = SocketManager.sharedSocket().getArea(floorId, areaId);
        setTitle(area.getName());
        initView();
    }

    private void initView(){

        deviceGroupTypes = new ArrayList<>();
        deviceGroupTypes.add(new DeviceGroupType(0, "场景", "device_group_scene"));
        deviceGroupTypes.add(new DeviceGroupType(1, "灯光", "device_group_light"));
        deviceGroupTypes.add(new DeviceGroupType(2, "窗帘", "device_group_curtain"));
        deviceGroupTypes.add(new DeviceGroupType(3, "温度", "device_group_temperature"));



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.device_type_recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AreaDetailActivity.this, 2, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        AreaDetailAdapter adapter = new AreaDetailAdapter(deviceGroupTypes);
        adapter.setOnDeviceTypeChoose(new AreaDetailAdapter.OnDeviceTypeChoose() {
            @Override
            public void onDeviceTypeClick(int position) {
//                Toast.makeText(AreaDetailActivity.this, "touch click:" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                String type = deviceGroupTypes.get(position).getName();

                intent.putExtra("DeviceGroupName", type);
                intent.putExtra("DeviceGroupType", position);

                intent.setClass(AreaDetailActivity.this, DeviceActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

    }

}