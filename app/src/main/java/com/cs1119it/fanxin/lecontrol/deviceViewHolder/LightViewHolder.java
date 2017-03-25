package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cs1119it.fanxin.lecontrol.DeviceActivity;
import com.cs1119it.fanxin.lecontrol.MyApplication;
import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.camGroupView.CamSwitchView;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.service.SocketConnect;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import static java.security.AccessController.getContext;

/**
 * Created by fanxin on 2017/3/25.
 */

public class LightViewHolder extends BaseDeviceViewHolder {
    CamSwitchView camSwitchView;
    TextView lightNameTv, lightDimmingTextView;
    Switch lightSwitchSwitch;
    SeekBar lightDimmingSeekBar;

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        lightNameTv.setText(device.getName());
        lightDimmingTextView.setText("调光");
        lightSwitchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Cam cam = device.getCamByCamType(20);
                LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), "00", isChecked?1:0);
                SocketManager.sharedSocket().socketConnect.sendMsg(leControlCode.message(true));
            }

        });

        lightDimmingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Cam cam = device.getCamByCamType(21);
                camSwitchView = new CamSwitchView(cam);
                LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), "01", seekBar.getProgress());
                SocketManager.sharedSocket().socketConnect.sendMsg(leControlCode.message(true));
            }
        });
    }

    public LightViewHolder(View itemView) {
        super(itemView);
        lightNameTv = (TextView) itemView.findViewById(R.id.cam_switch_textView);
        lightDimmingTextView = (TextView) itemView.findViewById(R.id.cam_slider_light_textView);
        lightSwitchSwitch = (Switch) itemView.findViewById(R.id.cam_switch_switch);
        lightDimmingSeekBar = (SeekBar) itemView.findViewById(R.id.cam_slider_light_seekBar);
        lightDimmingSeekBar.setMax(255);
    }

}
