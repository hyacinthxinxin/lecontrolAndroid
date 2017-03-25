package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.view.View;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Device;

/**
 * Created by fanxin on 2017/3/25.
 */

public class AirConditioningViewHolder extends BaseDeviceViewHolder {
    TextView airConditioningNameTv;
    TextView airConditioningTemperatureTv;
    TextView airConditioningSpeedTv;
    TextView airConditioningModeTv;


    @Override
    public void setDevice(Device device) {
        super.setDevice(device);
        airConditioningNameTv.setText(this.device.getName());
    }

    public AirConditioningViewHolder(View itemView) {
        super(itemView);
        airConditioningNameTv = (TextView) itemView.findViewById(R.id.cam_switch_textView);
    }

}
