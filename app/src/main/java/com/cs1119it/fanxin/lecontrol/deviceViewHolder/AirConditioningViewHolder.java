package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
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
    private TextView airConditioningTemperatureTv;
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
        airConditioningTemperatureTv = (TextView) airConditioningTemperatureView.findViewById(R.id.cam_temperature_value_textView);
        airConditioningTemperatureSeekBar = (SeekBar) airConditioningTemperatureView.findViewById(R.id.cam_slider_temperature_seekBar);
        airConditioningTemperatureSeekBar.setMax(maxTemperature - minTemperature);

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
                            cam.setControlValue(isChecked ? 1 : 0);
                            LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), cam.getControlValue());
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
            String t = cam.getControlValue() + "℃";
            airConditioningTemperatureTv.setText(t);
            airConditioningTemperatureSeekBar.setProgress(cam.getControlValue() - minTemperature);
            airConditioningTemperatureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    String t = String.valueOf(seekBar.getProgress() + minTemperature) + "℃";
                    airConditioningTemperatureTv.setText(t);
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
                            cam.setControlValue(seekBar.getProgress() + minTemperature);
                            LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), cam.getControlValue());
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
        if (airConditioningSpeedCams.size() > 0) {
            for (Cam cam : airConditioningSpeedCams) {
                switch (cam.getiType()) {
                    case 72:
                        airConditioningSpeedRadioButton1.setText(cam.getName());
                        airConditioningSpeedRadioButton1.setChecked(cam.isChecked());
                        airConditioningSpeedRadioButton1.setVisibility(View.VISIBLE);
                        break;
                    case 73:
                        airConditioningSpeedRadioButton2.setText(cam.getName());
                        airConditioningSpeedRadioButton2.setChecked(cam.isChecked());
                        airConditioningSpeedRadioButton2.setVisibility(View.VISIBLE);
                        break;
                    case 74:
                        airConditioningSpeedRadioButton3.setText(cam.getName());
                        airConditioningSpeedRadioButton3.setChecked(cam.isChecked());
                        airConditioningSpeedRadioButton3.setVisibility(View.VISIBLE);
                        break;
                    case 75:
                        airConditioningSpeedRadioButton4.setText(cam.getName());
                        airConditioningSpeedRadioButton4.setChecked(cam.isChecked());
                        airConditioningSpeedRadioButton4.setVisibility(View.VISIBLE);
                        break;
                    case 76:
                        airConditioningSpeedRadioButton5.setText(cam.getName());
                        airConditioningSpeedRadioButton5.setChecked(cam.isChecked());
                        airConditioningSpeedRadioButton5.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
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
        } else {
            airConditioningSpeedView.setVisibility(View.GONE);
        }
    }



    private void setupAirConditioningModeView() {
        List<Cam> airConditioningModeCams = device.getCamsIn(Cam.airConditioningModeCamTypes);
        if (airConditioningModeCams.size() > 0) {
            for (Cam cam : airConditioningModeCams) {
                switch (cam.getiType()) {
                    case 42:
                        airConditioningModeRadioButton1.setText(cam.getName());
                        airConditioningModeRadioButton1.setChecked(cam.isChecked());
                        airConditioningModeRadioButton1.setVisibility(View.VISIBLE);
                        break;
                    case 43:
                        airConditioningModeRadioButton2.setText(cam.getName());
                        airConditioningModeRadioButton2.setChecked(cam.isChecked());
                        airConditioningModeRadioButton2.setVisibility(View.VISIBLE);
                        break;
                    case 44:
                        airConditioningModeRadioButton3.setText(cam.getName());
                        airConditioningModeRadioButton3.setChecked(cam.isChecked());
                        airConditioningModeRadioButton3.setVisibility(View.VISIBLE);
                        break;
                    case 45:
                        airConditioningModeRadioButton4.setText(cam.getName());
                        airConditioningModeRadioButton4.setChecked(cam.isChecked());
                        airConditioningModeRadioButton4.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
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
        } else {
            airConditioningModeView.setVisibility(View.GONE);
        }

    }

}
