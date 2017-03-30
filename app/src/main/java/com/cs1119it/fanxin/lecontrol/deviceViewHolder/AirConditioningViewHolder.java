package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.support.annotation.IdRes;
import android.util.Log;
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

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by fanxin on 2017/3/25.
 */

public class AirConditioningViewHolder extends BaseDeviceViewHolder {
    private static Integer minTemperature = 19;
    private static Integer maxTemperature = 32;

    private View airConditioningSwitchView;
    private TextView airConditioningNameTv;
    private Switch airConditioningSwitchSwitch;//开关 40

    private View airConditioningTemperatureView;
    private SeekBar airConditioningTemperatureSeekBar;//调温  41

    private View airConditioningSpeedView;
    private RadioGroup airConditioningSpeedRadioGroup;
    private RadioButton airConditioningSpeedRadioButton1;//微风   72
    private RadioButton airConditioningSpeedRadioButton2;//低风   46
    private RadioButton airConditioningSpeedRadioButton3;//中风   47
    private RadioButton airConditioningSpeedRadioButton4;//高风   48
    private RadioButton airConditioningSpeedRadioButton5;//强风   76

    private View airConditioningModeView;
    private RadioGroup airConditioningModeRadioGroup;
    private RadioButton airConditioningModeRadioButton1;//制热   42
    private RadioButton airConditioningModeRadioButton2;//制冷   43
    private RadioButton airConditioningModeRadioButton3;//通风   44
    private RadioButton airConditioningModeRadioButton4;//除湿   45

    public AirConditioningViewHolder(View itemView) {
        super(itemView);
        airConditioningSwitchView = itemView.findViewById(R.id.device_air_conditioning_switch);
        airConditioningNameTv = (TextView) airConditioningSwitchView.findViewById(R.id.cam_switch_textView);
        airConditioningSwitchSwitch = (Switch) airConditioningSwitchView.findViewById(R.id.cam_switch_switch);

        airConditioningTemperatureView = itemView.findViewById(R.id.device_air_conditioning_temperature);
        airConditioningTemperatureSeekBar = (SeekBar) airConditioningTemperatureView.findViewById(R.id.cam_slider_temperature_seekBar);
        airConditioningTemperatureSeekBar.setMax(maxTemperature-minTemperature);

        airConditioningSpeedView = itemView.findViewById(R.id.device_air_conditioning_speed);
        airConditioningSpeedRadioGroup = (RadioGroup) airConditioningSpeedView.findViewById(R.id.cam_speed_radioGroup);
        airConditioningSpeedRadioButton1 = (RadioButton) airConditioningSpeedRadioGroup.findViewById(R.id.cam_speed1);
        airConditioningSpeedRadioButton2 = (RadioButton) airConditioningSpeedRadioGroup.findViewById(R.id.cam_speed2);
        airConditioningSpeedRadioButton3 = (RadioButton) airConditioningSpeedRadioGroup.findViewById(R.id.cam_speed3);
        airConditioningSpeedRadioButton4 = (RadioButton) airConditioningSpeedRadioGroup.findViewById(R.id.cam_speed4);
        airConditioningSpeedRadioButton5 = (RadioButton) airConditioningSpeedRadioGroup.findViewById(R.id.cam_speed5);

        airConditioningModeView = itemView.findViewById(R.id.device_air_conditioning_mode);
        airConditioningModeRadioGroup = (RadioGroup) airConditioningModeView.findViewById(R.id.cam_mode_radioGroup);
        airConditioningModeRadioButton1 = (RadioButton) airConditioningModeRadioGroup.findViewById(R.id.cam_mode_heating_radioButton);
        airConditioningModeRadioButton2 = (RadioButton) airConditioningModeRadioGroup.findViewById(R.id.cam_mode_refrigeration_radioButton);
        airConditioningModeRadioButton3 = (RadioButton) airConditioningModeRadioGroup.findViewById(R.id.cam_mode_ventilation_radioButton);
        airConditioningModeRadioButton4 = (RadioButton) airConditioningModeRadioGroup.findViewById(R.id.cam_mode_desiccant_radioButton);

    }

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        setupAirConditioningSwitchView();
        setupAirConditioningTemperatureView();
        setupAirConditioningSpeedView();
        setupAirConditioningModeView();
    }

    private void setupAirConditioningSwitchView() {
        airConditioningNameTv.setText(device.getName());
        final Cam cam = device.getCamByCamType(40);
        if (cam != null) {
            airConditioningSwitchSwitch.setChecked(cam.getControlValue().equals(1));
            airConditioningSwitchSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean isChecked = ((Switch) v).isChecked();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), isChecked ? 1 : 0);
                            SocketManager.sharedSocket().sendMsg(leControlCode.message(true));

                        }
                    }.start();
                }
            });
        } else {
            airConditioningSwitchView.setVisibility(GONE);
        }
    }

    private void setupAirConditioningTemperatureView() {
        final Cam cam = device.getCamByCamType(41);
        if (cam != null) {
            airConditioningTemperatureSeekBar.setProgress(cam.getControlValue()-minTemperature);
            airConditioningTemperatureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
            airConditioningTemperatureView.setVisibility(GONE);
        }
    }

    private void setupAirConditioningSpeedView() {
        List<Cam> airConditioningSpeedCams = device.getCamsIn(Cam.airConditioningSpeedCamTypes);
        if (airConditioningSpeedCams.size()>0) {
            for (Cam cam: airConditioningSpeedCams) {

            }
        } else {
            airConditioningSpeedView.setVisibility(View.GONE);
        }

        Cam cam1 = device.getCamByCamType(72);
        if (cam1 != null) {
            airConditioningSpeedRadioButton1.setText(cam1.getName());
            airConditioningSpeedRadioButton1.setChecked(cam1.isChecked());
        } else {
            airConditioningSpeedRadioButton1.setVisibility(View.GONE);
        }
        Cam cam2 = device.getCamByCamType(46);
        if (cam2 != null) {
            airConditioningSpeedRadioButton2.setText(cam2.getName());
            airConditioningSpeedRadioButton2.setChecked(cam2.isChecked());
        } else {
            airConditioningSpeedRadioButton2.setVisibility(View.GONE);
        }
        Cam cam3 = device.getCamByCamType(47);
        if (cam3 != null) {
            airConditioningSpeedRadioButton3.setText(cam3.getName());
            airConditioningSpeedRadioButton3.setChecked(cam3.isChecked());
        } else {
            airConditioningSpeedRadioButton3.setVisibility(View.GONE);
        }
        Cam cam4 = device.getCamByCamType(48);
        if (cam4 != null) {
            airConditioningSpeedRadioButton4.setText(cam4.getName());
            airConditioningSpeedRadioButton4.setChecked(cam4.isChecked());
        } else {
            airConditioningSpeedRadioButton4.setVisibility(View.GONE);
        }
        Cam cam5 = device.getCamByCamType(76);
        if (cam5 != null) {
            airConditioningSpeedRadioButton5.setText(cam5.getName());
            airConditioningSpeedRadioButton5.setChecked(cam5.isChecked());
        } else {
            airConditioningSpeedRadioButton5.setVisibility(View.GONE);
        }
        if (cam1 == null && cam2 == null && cam3 == null && cam4 == null && cam5 == null) {
            airConditioningSpeedView.setVisibility(View.GONE);
        } else {
            airConditioningSpeedRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    int radioButtonId = group.getCheckedRadioButtonId();
                    final RadioButton rb = (RadioButton) airConditioningSpeedView.findViewById(radioButtonId);
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

    private void setupAirConditioningModeView() {
        List<Cam> airConditioningModeCams = device.getCamsIn(Cam.airConditioningModeCamTypes);
        if (airConditioningModeCams.size()>0) {

        } else {

        }
        Cam cam1 = device.getCamByCamType(42);
        if (cam1 != null) {
            airConditioningModeRadioButton1.setText(cam1.getName());
        } else {
            airConditioningModeRadioButton1.setVisibility(View.GONE);
        }
        Cam cam2 = device.getCamByCamType(43);
        if (cam2 != null) {
            airConditioningModeRadioButton2.setText(cam2.getName());
        } else {
            airConditioningModeRadioButton2.setVisibility(View.GONE);
        }
        Cam cam3 = device.getCamByCamType(44);
        if (cam3 != null) {
            airConditioningModeRadioButton3.setText(cam3.getName());
        } else {
            airConditioningModeRadioButton3.setVisibility(View.GONE);
        }
        Cam cam4 = device.getCamByCamType(45);
        if (cam4 != null) {
            airConditioningModeRadioButton4.setText(cam4.getName());
        } else {
            airConditioningModeRadioButton4.setVisibility(View.GONE);
        }
        if (cam1 == null && cam2 == null && cam3 == null && cam4 == null) {
            airConditioningModeView.setVisibility(View.GONE);
        } else {
            airConditioningModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    int radioButtonId = group.getCheckedRadioButtonId();
                    final RadioButton rb = (RadioButton) airConditioningModeView.findViewById(radioButtonId);
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
