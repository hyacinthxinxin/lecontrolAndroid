package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Device;

/**
 * Created by fanxin on 2017/3/25.
 */


public class SceneViewHolder extends BaseDeviceViewHolder {
    public TextView sceneNameTv;

    @Override
    public void setDevice(Device device) {
        super.setDevice(device);
        this.device = device;
        sceneNameTv = (TextView) itemView.findViewById(R.id.scene_textView);
        sceneNameTv.setText(device.getName());

    }

    public SceneViewHolder(View itemView) {
        super(itemView);
    }

}
