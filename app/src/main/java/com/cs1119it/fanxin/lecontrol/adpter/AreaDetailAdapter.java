package com.cs1119it.fanxin.lecontrol.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Area;

import java.util.List;

/**
 * Created by fanxin on 2017/3/22.
 */

public class AreaDetailAdapter extends RecyclerView.Adapter {
    public interface OnDeviceTypeChoose {
        void onDeviceTypeClick(int position);
    }
    private OnDeviceTypeChoose onDeviceTypeChoose;
    public void setOnDeviceTypeChoose(OnDeviceTypeChoose onDeviceTypeChoose) {
        this.onDeviceTypeChoose = onDeviceTypeChoose;
    }

    private List<String> deviceTypes;

    public AreaDetailAdapter(List<String> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    private class DeviceTypeViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private int position;
        private TextView deviceTypeTv;
        private ImageView deviceTypeIv;

        private DeviceTypeViewHolder(View itemView) {
            super(itemView);
            deviceTypeTv = (TextView) itemView.findViewById(R.id.device_type_name);
            deviceTypeIv = (ImageView) itemView.findViewById(R.id.device_type_image);
            rootView = itemView.findViewById(R.id.device_type_item);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onDeviceTypeChoose) {
                        onDeviceTypeChoose.onDeviceTypeClick(position);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_type_item, null);
        return new DeviceTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DeviceTypeViewHolder h = (DeviceTypeViewHolder) holder;
        h.position = h.getAdapterPosition();
        String name = deviceTypes.get(position);
        h.deviceTypeTv.setText(name);
    }

    @Override
    public int getItemCount() {
        return deviceTypes.size();
    }


}
