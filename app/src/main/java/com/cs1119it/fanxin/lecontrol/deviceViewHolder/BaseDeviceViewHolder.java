package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cs1119it.fanxin.lecontrol.model.Device;

/**
 * Created by fanxin on 2017/3/25.
 */

public class BaseDeviceViewHolder extends RecyclerView.ViewHolder {
    public Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public BaseDeviceViewHolder(View itemView) {
        super(itemView);
    }

}
