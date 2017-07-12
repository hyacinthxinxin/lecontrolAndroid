package com.cs1119it.fanxin.lecontrol.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.deviceViewHolder.AirConditioningViewHolder;
import com.cs1119it.fanxin.lecontrol.deviceViewHolder.BaseDeviceViewHolder;
import com.cs1119it.fanxin.lecontrol.deviceViewHolder.CurtainViewHolder;
import com.cs1119it.fanxin.lecontrol.deviceViewHolder.EnvironmentViewHolder;
import com.cs1119it.fanxin.lecontrol.deviceViewHolder.FloorHeatViewHolder;
import com.cs1119it.fanxin.lecontrol.deviceViewHolder.FreshAirViewHolder;
import com.cs1119it.fanxin.lecontrol.deviceViewHolder.LightViewHolder;
import com.cs1119it.fanxin.lecontrol.deviceViewHolder.SceneViewHolder;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.util.List;
import java.util.Vector;

/**
 * Created by fanxin on 2017/3/22.
 */

public class DeviceAdapter extends RecyclerView.Adapter {

    Vector<Boolean> vector = new Vector<>(); //only for scenes

    private List<Device> devices;

    public DeviceAdapter(List<Device> devices) {
        this.devices = devices;
        for(Device device :devices){
            vector.add(false);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Device device = devices.get(position);
        return device.getiType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_scene_item, null);
                SceneViewHolder sceneViewHolder = new SceneViewHolder(view);
                sceneViewHolder.setOnSceneChoose(new SceneViewHolder.OnSceneChoose() {
                    @Override
                    public void onSceneChoose(int position) {
                        for (Device device: devices) {
                            device.setChecked(false);
                        }
                        final Device device = devices.get(position);
                        device.setChecked(true);
                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                for (Cam cam: device.getCams()) {
                                    LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), cam.getControlValue());
                                    SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                                }
                            }
                        }.start();
                        notifyDataSetChanged();
                    }
                });
                return sceneViewHolder;
            case 1:
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_light_item, null);
                return new LightViewHolder(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_curtain_item, null);
                return new CurtainViewHolder(view);
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_air_conditioning_item, null);
                return new AirConditioningViewHolder(view);
            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_floor_heat_item, null);
                return new FloorHeatViewHolder(view);
            case 6:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_fresh_air_item, null);
                return new FreshAirViewHolder(view);
            case 7:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_environment_item, null);
                return new EnvironmentViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseDeviceViewHolder h = (BaseDeviceViewHolder) holder;
        if (h instanceof SceneViewHolder) {
            ((SceneViewHolder) h).position = h.getAdapterPosition();
        }
        h.setDevice(devices.get(position));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

}
