package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.view.View;
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
 * Created by fanxin on 2017/7/12.
 */

public class EnvironmentViewHolder extends BaseDeviceViewHolder {
    private View environmentTemperatureView, environmentHumidityView, environmentPMView;
    private TextView environmentTemperatureNameTextView, environmentTemperatureValueTextView;
    private TextView environmentHumidityNameTextView, environmentHumidityValueTextView;
    private TextView environmentPMNameTextView, environmentPMValueTextView;

    public EnvironmentViewHolder(View itemView) {
        super(itemView);
        environmentTemperatureView = itemView.findViewById(R.id.environment_temperature);
        environmentTemperatureNameTextView = (TextView) environmentTemperatureView.findViewById(R.id.cam_display_name_textView);
        environmentTemperatureValueTextView = (TextView) environmentTemperatureView.findViewById(R.id.cam_display_value_textView);

        environmentHumidityView = itemView.findViewById(R.id.environment_humidity);
        environmentHumidityNameTextView = (TextView) environmentHumidityView.findViewById(R.id.cam_display_name_textView);
        environmentHumidityValueTextView = (TextView) environmentHumidityView.findViewById(R.id.cam_display_value_textView);

        environmentPMView = itemView.findViewById(R.id.environment_pm);
        environmentPMNameTextView = (TextView) environmentPMView.findViewById(R.id.cam_display_name_textView);
        environmentPMValueTextView = (TextView) environmentPMView.findViewById(R.id.cam_display_value_textView);


    }

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        setupEnvironmentTemperatureView();
        setupEnvironmentHumidityView();
        setupEnvironmentPMView();
    }

    private void setupEnvironmentTemperatureView() {
        if (device.getCamByCamType(90) != null) {
            Cam cam = device.getCamByCamType(90);
            environmentTemperatureNameTextView.setText(cam.getName());
            String text = String.valueOf(cam.getControlValue())+"℃";
            environmentTemperatureValueTextView.setText(text);
        } else {
            environmentTemperatureView.setVisibility(GONE);
        }
    }

    private void setupEnvironmentHumidityView() {

        if (device.getCamByCamType(91) != null) {
            Cam cam = device.getCamByCamType(91);
            environmentHumidityNameTextView.setText(cam.getName());
            String text = String.valueOf(cam.getControlValue())+"%RH";
            environmentHumidityValueTextView.setText(text);
        } else {
            environmentHumidityView.setVisibility(GONE);
        }
    }
    private void setupEnvironmentPMView(){

        if (device.getCamByCamType(92) != null) {
            Cam cam = device.getCamByCamType(92);
            environmentPMNameTextView.setText(cam.getName());
            String text = String.valueOf(cam.getControlValue())+"μg/m³";
            environmentPMValueTextView.setText(text);
        } else {
            environmentPMView.setVisibility(GONE);
        }
    }

}
