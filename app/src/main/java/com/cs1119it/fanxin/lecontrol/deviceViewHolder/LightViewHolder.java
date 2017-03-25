package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Device;

/**
 * Created by fanxin on 2017/3/25.
 */

public class LightViewHolder extends BaseDeviceViewHolder {
    TextView lightNameTv;

    @Override
    public void setDevice(Device device) {
        super.setDevice(device);
    }

    public LightViewHolder(View itemView) {
        super(itemView);
        lightNameTv = (TextView) itemView.findViewById(R.id.switch_textView);
    }
}
