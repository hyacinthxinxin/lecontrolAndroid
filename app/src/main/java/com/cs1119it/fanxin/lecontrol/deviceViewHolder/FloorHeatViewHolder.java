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

public class FloorHeatViewHolder extends BaseDeviceViewHolder {
    private View floorHeatSwitchView;
    private TextView floorHeatNameTv;
    private Switch floorHeatSwitchSwitch;//开关 50
    private View floorHeatTemperatureView;
    private SeekBar floorHeatTemperatureSeekBar;//调温  51

    public FloorHeatViewHolder(View itemView) {
        super(itemView);
        floorHeatSwitchView = itemView.findViewById(R.id.device_floor_heat_switch);
        floorHeatNameTv = (TextView) floorHeatSwitchView.findViewById(R.id.cam_switch_textView);
        floorHeatSwitchSwitch = (Switch) floorHeatSwitchView.findViewById(R.id.cam_switch_switch);

        floorHeatTemperatureView = itemView.findViewById(R.id.device_floor_heat_temperature);
        floorHeatTemperatureSeekBar = (SeekBar) floorHeatTemperatureView.findViewById(R.id.cam_slider_temperature_seekBar);
        floorHeatTemperatureSeekBar.setMax(13);

    }

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        setupFloorHeatSwitchView();
        setupFloorHeatTemperatureView();
    }

    private void setupFloorHeatSwitchView() {
        floorHeatNameTv.setText(device.getName());
        floorHeatSwitchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                new Thread() {
                    @Override
                    public void run() {
                        Cam cam = device.getCamByCamType(50);
                        LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), isChecked ? 1 : 0);
                        SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                        super.run();
                    }
                }.start();
            }

        });
    }

    private void setupFloorHeatTemperatureView() {
        floorHeatTemperatureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Cam cam = device.getCamByCamType(51);
                        LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), seekBar.getProgress() + 18);
                        SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                    }
                }.start();
            }
        });

    }

}
