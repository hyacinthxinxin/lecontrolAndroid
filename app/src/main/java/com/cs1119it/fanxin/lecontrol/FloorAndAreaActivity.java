package com.cs1119it.fanxin.lecontrol;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cs1119it.fanxin.lecontrol.adpter.FloorAndAreaAdapter;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Building;
import com.cs1119it.fanxin.lecontrol.model.Floor;
import com.cs1119it.fanxin.lecontrol.service.SocketService;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FloorAndAreaActivity extends AppCompatActivity {
    private List<Area> areas;
    private RecyclerView recyclerView;
    private FloorAndAreaAdapter floorAndAreaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent_socketService = new Intent(this, SocketService.class);
        startService(intent_socketService);
        setContentView(R.layout.activity_floor_and_area);
        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SocketManager.sharedSocket().isNeedRefresh()) {
            initData();
            initView();
            SocketManager.sharedSocket().setNeedRefresh(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_floor_and_area, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent_socketService = new Intent(this, SocketService.class);
        stopService(intent_socketService);
    }

    private void initData() {
        areas = new ArrayList<>();
        Building building = SocketManager.sharedSocket().getDataModel().getBuilding();

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.home_app_bar_layout);
        Toolbar toolbar = (Toolbar) appBarLayout.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(FloorAndAreaActivity.this, LoginActivity.class));
                return true;
            }
        });
        TextView customTitleTextView = (TextView) toolbar.findViewById(R.id.custom_title_textView);
        customTitleTextView.setText(building.getName());
        setTitle("");

        for (int i = 0; i < building.getFloors().size(); i++) {
            Floor floor = building.getFloors().get(i);
            Area area = new Area(floor.getName(), "");
            if (building.getFloors().size() > 1) {
                //添加虚拟的房间作为楼层的显示模型数据
                area.setViewType(0);
                areas.add(area);
            }
            for (int j = 0; j < floor.getAreas().size(); j++) {
                area = floor.getAreas().get(j);
                area.setViewType(1);
                areas.add(area);
            }
        }
    }


    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.floor_and_area_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FloorAndAreaActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        floorAndAreaAdapter = new FloorAndAreaAdapter(areas);
        floorAndAreaAdapter.setOnAreaClickListener(new FloorAndAreaAdapter.OnAreaClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(FloorAndAreaActivity.this, AreaDetailActivity.class);
                Area area = areas.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("area", area);
//                intent.putExtras(bundle);
                intent.putExtra("floorId", area.getFloorId());
                intent.putExtra("areaId", area.getAreaId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(floorAndAreaAdapter);
    }
}
