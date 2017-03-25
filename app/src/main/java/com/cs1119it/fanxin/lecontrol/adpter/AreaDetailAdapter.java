package com.cs1119it.fanxin.lecontrol.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.DeviceGroupType;
import com.cs1119it.fanxin.lecontrol.unit.Constant;

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

    private List<DeviceGroupType> deviceGroupTypes;

    public AreaDetailAdapter(List<DeviceGroupType> deviceGroupTypes) {
        this.deviceGroupTypes = deviceGroupTypes;
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
        String name = deviceGroupTypes.get(position).getName();
        h.deviceTypeTv.setText(name);
        String imageName = deviceGroupTypes.get(position).getImageName();
        h.deviceTypeIv.setImageResource(Constant.getDeviceTypeImage(imageName));
    }

    @Override
    public int getItemCount() {
        return deviceGroupTypes.size();
    }

}
