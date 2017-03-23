package com.cs1119it.fanxin.lecontrol.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Device;

import java.util.List;

/**
 * Created by fanxin on 2017/3/22.
 */

public class DeviceAdapter extends RecyclerView.Adapter {
    private List<Device> devices;

    public DeviceAdapter(List<Device> devices) {
        this.devices = devices;
    };

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
                return new DeviceAdapter.SceneViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_light_item, null);
//                View lightSliderView = view.findViewById(R.id.cam_slider_light);
//                lightSliderView.setVisibility(View.GONE);
                return new DeviceAdapter.LightViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_curtain_item, null);
                return new DeviceAdapter.CurtainViewHolder(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_air_conditioning_item, null);
                return new DeviceAdapter.AirConditioningViewHolder(view);
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_floor_heat_item, null);
                return new DeviceAdapter.FloorHeatViewHolder(view);
            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_fresh_air_item, null);
                return new DeviceAdapter.FreshAirViewHolder(view);
            default:
                System.out.println("default");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    private class SceneViewHolder extends RecyclerView.ViewHolder {
        TextView sceneNameTv;

        private SceneViewHolder(View itemView) {
            super(itemView);
            sceneNameTv = (TextView) itemView.findViewById(R.id.scene_textView);
        }
    }

    private class LightViewHolder extends RecyclerView.ViewHolder {
        TextView lightNameTv;

        private LightViewHolder(View itemView) {
            super(itemView);
            lightNameTv = (TextView) itemView.findViewById(R.id.switch_textView);
        }
    }

    private class CurtainViewHolder extends RecyclerView.ViewHolder {
        private CurtainViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class AirConditioningViewHolder extends RecyclerView.ViewHolder {
        private AirConditioningViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class FloorHeatViewHolder extends RecyclerView.ViewHolder {
        private FloorHeatViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class FreshAirViewHolder extends RecyclerView.ViewHolder {
        private FreshAirViewHolder(View itemView) {
            super(itemView);
        }
    }

}
