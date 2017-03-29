package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

/**
 * Created by fanxin on 2017/3/25.
 */

public class LightViewHolder extends BaseDeviceViewHolder {
    private TextView lightNameTv, lightDimmingTextView;
    private Switch lightSwitchSwitch;
    private SeekBar lightDimmingSeekBar;

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        lightNameTv.setText(device.getName());
        lightDimmingTextView.setText("调光");

        lightSwitchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                new Thread(){
                    @Override
                    public void run() {
                        LeControlCode leControlCode;
                        if (device.getCamByCamType(10) != null) {
                            Cam cam = device.getCamByCamType(10);
                            leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), isChecked?1:0);
                            SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                        } else {
                            Cam cam = device.getCamByCamType(20);
                            leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), isChecked?1:0);
                            SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                        }
                        super.run();
                    }
                }.start();
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
            public void onStopTrackingTouch(final SeekBar seekBar) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Cam cam = device.getCamByCamType(21);
                        LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), "01", seekBar.getProgress());
                        SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                    }
                }.start();
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
