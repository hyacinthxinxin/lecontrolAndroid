package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.presenter.CamSwitchPresenter;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import static android.view.View.GONE;

/**
 * Created by fanxin on 2017/3/25.
 */

public class LightViewHolder extends BaseDeviceViewHolder {
    private View lightSwitchView, lightDimmingView;
    private TextView lightNameTv, lightDimmingTextView;
    private Switch lightSwitchSwitch;
    private SeekBar lightDimmingSeekBar;

    public LightViewHolder(View itemView) {
        super(itemView);
        lightSwitchView = itemView.findViewById(R.id.light_switch);
        lightNameTv = (TextView) itemView.findViewById(R.id.cam_switch_textView);

        lightDimmingView = itemView.findViewById(R.id.light_dimming);
        lightDimmingTextView = (TextView) itemView.findViewById(R.id.cam_slider_light_textView);
        lightSwitchSwitch = (Switch) itemView.findViewById(R.id.cam_switch_switch);
        lightDimmingSeekBar = (SeekBar) itemView.findViewById(R.id.cam_slider_light_seekBar);
        lightDimmingSeekBar.setMax(255);
    }

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        setupLightSwitchView();
        setupLightTemperatureView();
    }

    private void setupLightSwitchView() {
        lightNameTv.setText(device.getName());
        if (device.getCamByCamType(10) != null) {
            Cam cam = device.getCamByCamType(10);
            lightSwitchSwitch.setChecked(cam.getControlValue().equals(1));
        } else if (device.getCamByCamType(20) != null) {
            Cam cam = device.getCamByCamType(20);
            lightSwitchSwitch.setChecked(cam.getControlValue().equals(1));
        } else {
            lightSwitchView.setVisibility(GONE);
        }

        lightSwitchSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isChecked = ((Switch) v).isChecked();
                new Thread() {
                    @Override
                    public void run() {
                        LeControlCode leControlCode;
                        if (device.getCamByCamType(10) != null) {
                            Cam cam = device.getCamByCamType(10);
                            leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), isChecked ? 1 : 0);
                            SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                        } else {
                            Cam cam = device.getCamByCamType(20);
                            leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), isChecked ? 1 : 0);
                            SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                        }
                        super.run();
                    }
                }.start();
            }
        });
    }

    private void setupLightTemperatureView() {
        Cam cam = device.getCamByCamType(21);
        if (cam != null) {
            lightDimmingTextView.setText("调光");
            lightDimmingSeekBar.setProgress(cam.getControlValue());
            lightDimmingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                            Cam cam = device.getCamByCamType(21);
                            LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), "01", seekBar.getProgress());
                            SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                        }
                    }.start();
                }
            });
        } else {
            lightDimmingView.setVisibility(GONE);
        }
    }

}
