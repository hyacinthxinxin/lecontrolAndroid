package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class FreshAirViewHolder extends BaseDeviceViewHolder {

    private View freshAirSwitchView;
    private TextView freshAirNameTv;
    private Switch freshAirSwitchSwitch;//开关 60

    private View freshAirSpeedView;
    private RadioGroup freshAirSpeedRadioGroup;
    private RadioButton freshAirSpeedRadioButton1;//微风   61
    private RadioButton freshAirSpeedRadioButton2;//低风   62
    private RadioButton freshAirSpeedRadioButton3;//中风   63

    public FreshAirViewHolder(View itemView) {
        super(itemView);
        freshAirSwitchView = itemView.findViewById(R.id.device_fresh_air_switch);
        freshAirNameTv = (TextView) freshAirSwitchView.findViewById(R.id.cam_switch_textView);
        freshAirSwitchSwitch = (Switch) freshAirSwitchView.findViewById(R.id.cam_switch_switch);

        freshAirSpeedView = itemView.findViewById(R.id.device_fresh_air_speed);
        freshAirSpeedRadioGroup = (RadioGroup) freshAirSpeedView.findViewById(R.id.cam_speed_radioGroup);
        freshAirSpeedRadioButton1 = (RadioButton) freshAirSpeedRadioGroup.findViewById(R.id.cam_speed1);
        freshAirSpeedRadioButton2 = (RadioButton) freshAirSpeedRadioGroup.findViewById(R.id.cam_speed2);
        freshAirSpeedRadioButton3 = (RadioButton) freshAirSpeedRadioGroup.findViewById(R.id.cam_speed3);

        // TODO: 2017/3/29
        freshAirSpeedRadioGroup.findViewById(R.id.cam_speed4).setVisibility(GONE);
        freshAirSpeedRadioGroup.findViewById(R.id.cam_speed5).setVisibility(GONE);
    }

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        setupFreshAirSwitchView();
        setupFreshAirSpeedView();
    }

    private void setupFreshAirSwitchView() {
        final Cam cam = device.getCamByCamType(60);
        if (cam != null) {
            freshAirNameTv.setText(device.getName());
            freshAirSwitchSwitch.setChecked(cam.getControlValue().equals(1));
            freshAirSwitchSwitch.setOnClickListener(new View.OnClickListener() {
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
            freshAirSwitchView.setVisibility(GONE);
        }
    }

    private void setupFreshAirSpeedView() {
        Cam cam1 = device.getCamByCamType(61);
        if (cam1 != null) {
            freshAirSpeedRadioButton1.setText(cam1.getName());
        } else {
            freshAirSpeedRadioButton1.setVisibility(GONE);
        }
        Cam cam2 = device.getCamByCamType(62);
        if (cam2 != null) {
            freshAirSpeedRadioButton2.setText(cam2.getName());
        } else {
            freshAirSpeedRadioButton2.setVisibility(GONE);
        }
        Cam cam3 = device.getCamByCamType(63);
        if (cam3 != null) {
            freshAirSpeedRadioButton3.setText(cam3.getName());
        } else {
            freshAirSpeedRadioButton3.setVisibility(GONE);
        }

        if (cam1 == null && cam2 == null && cam3 == null) {
            freshAirSpeedView.setVisibility(GONE);
        } else {
            freshAirSpeedRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    int radioButtonId = group.getCheckedRadioButtonId();
                    final RadioButton rb = (RadioButton) freshAirSpeedView.findViewById(radioButtonId);
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Cam cam = device.getCamByCamName(rb.getText().toString());
                            if (cam != null) {
                                LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), cam.getControlValue());
                                SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                            }
                        }
                    }.start();
                }
            });
        }
    }


}
