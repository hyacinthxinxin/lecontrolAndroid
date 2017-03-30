package com.cs1119it.fanxin.lecontrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.cs1119it.fanxin.lecontrol.adpter.AreaDetailAdapter;
import com.cs1119it.fanxin.lecontrol.adpter.BuildingAdapter;
import com.cs1119it.fanxin.lecontrol.model.Building;
import com.cs1119it.fanxin.lecontrol.model.SimpleBuilding;
import com.cs1119it.fanxin.lecontrol.unit.ParseJson;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;
import com.cs1119it.fanxin.lecontrol.unit.TingSpectrumConnect;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ConfigActivity extends AppCompatActivity {
    List<SimpleBuilding> simpleBuildings;
    private final LoadBuildingsHandler loadBuildingsHandler = new LoadBuildingsHandler(this);
    private final LoadBuildingDetailHandler loadBuildingDetailHandler = new LoadBuildingDetailHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.config_app_bar_layout);
        Toolbar toolbar = (Toolbar) appBarLayout.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.logo);

        getBuildings();

    }

    private static class LoadBuildingsHandler extends Handler {
        private final WeakReference<ConfigActivity> configActivityWeakReference;

        private LoadBuildingsHandler(ConfigActivity activity) {
            configActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final ConfigActivity activity = configActivityWeakReference.get();

            String string = msg.getData().getString("simpleBuildings");
            try {
                JSONArray jsonArray = new JSONArray(string);
                activity.simpleBuildings = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonOb = jsonArray.getJSONObject(i);
                    SimpleBuilding simpleBuilding = new SimpleBuilding();
                    simpleBuilding.setName(jsonOb.getString("name"));
                    simpleBuilding.setImageName(jsonOb.getString("image_name"));
                    simpleBuilding.setBuildingId(jsonOb.getInt("id"));
                    simpleBuilding.setSocketAddress(jsonOb.getString("socket_address"));
                    simpleBuilding.setSocketPort(jsonOb.getInt("socket_port"));
                    activity.simpleBuildings.add(simpleBuilding);
                }
                // 此处可以更新UI
                RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.building_recycler_view);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                BuildingAdapter adapter = new BuildingAdapter(activity.simpleBuildings);

                adapter.setOnBuildingChoose(new BuildingAdapter.OnBuildingChoose() {
                    @Override
                    public void onBuildingClick(int position) {
//                        Intent intent = new Intent();
                        Integer buildingId = activity.simpleBuildings.get(position).getBuildingId();
                        TingSpectrumConnect.setBuild_id(buildingId);
                        activity.getBuildingDetail();
                    }
                });

                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private static class LoadBuildingDetailHandler extends Handler {
        private final WeakReference<ConfigActivity> configActivityWeakReference;
        private LoadBuildingDetailHandler(ConfigActivity activity) {
            configActivityWeakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final ConfigActivity activity = configActivityWeakReference.get();
            activity.startActivity(new Intent(activity, FloorAndAreaActivity.class));
        }
    }

    //获取项目信息
    private void getBuildings() {
        OkHttpUtils
                .get()
                .url("http://www.tingspectrum.com/api/v2/buildings")//
                .addParams("user_id", TingSpectrumConnect.getUser_id())
                .addHeader("access-token", TingSpectrumConnect.getAccessToken())
                .addHeader("client", TingSpectrumConnect.getClient())
                .addHeader("uid", TingSpectrumConnect.getUid())
                .addHeader("token-type", TingSpectrumConnect.getTokenType())
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        String string = response.body().string();
                        Message message = new Message();
                        Bundle b = new Bundle();// 存放数据
                        b.putString("simpleBuildings", string);
                        message.setData(b);
                        loadBuildingsHandler.sendMessage(message); // 向Handler发送消息，更新UI
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                    }
                });
    }

    private void getBuildingDetail() {
        OkHttpUtils
                .get()//
                .url("http://www.tingspectrum.com/api/v2/building_detail")//
                .addParams("building_id", TingSpectrumConnect.getBuild_id().toString())
                .addHeader("access-token", TingSpectrumConnect.getAccessToken())
                .addHeader("client", TingSpectrumConnect.getClient())
                .addHeader("uid", TingSpectrumConnect.getUid())
                .addHeader("token-type", TingSpectrumConnect.getTokenType())
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        String msg = response.body().string();
                        SocketManager.sharedSocket().setBuilding(ParseJson.parseBuildingDetail(msg));
                        loadBuildingDetailHandler.sendEmptyMessage(200);
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });
    }
}
