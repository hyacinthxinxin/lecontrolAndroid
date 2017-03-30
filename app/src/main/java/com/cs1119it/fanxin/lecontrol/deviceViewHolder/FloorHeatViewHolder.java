package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.graphics.ImageFormat;
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

import static android.view.View.GONE;

/**
 * Created by fanxin on 2017/3/25.
 */

public class FloorHeatViewHolder extends BaseDeviceViewHolder {
    private static Integer minTemperature = 19;
    private static Integer maxTemperature = 32;

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
        floorHeatTemperatureSeekBar.setMax(maxTemperature-minTemperature);

    }

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        setupFloorHeatSwitchView();
        setupFloorHeatTemperatureView();
    }

    private void setupFloorHeatSwitchView() {
        final Cam cam = device.getCamByCamType(50);
        if (cam != null) {
            floorHeatNameTv.setText(device.getName());
            floorHeatSwitchSwitch.setChecked(cam.getControlValue().equals(1));
            floorHeatSwitchSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean isChecked = ((Switch) v).isChecked();
                    new Thread() {
                        @Override
                        public void run() {
                            LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), isChecked ? 1 : 0);
                            SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                            super.run();
                        }
                    }.start();
                }

            });
        } else {
            floorHeatSwitchView.setVisibility(GONE);
        }

    }

    private void setupFloorHeatTemperatureView() {
        final Cam cam = device.getCamByCamType(51);
        if (cam != null) {
            floorHeatTemperatureSeekBar.setProgress(cam.getControlValue()-minTemperature);
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
                            LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), seekBar.getProgress() + minTemperature);
                            SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                        }
                    }.start();
                }
            });
        } else {
            floorHeatTemperatureView.setVisibility(GONE);
        }

    }

}
