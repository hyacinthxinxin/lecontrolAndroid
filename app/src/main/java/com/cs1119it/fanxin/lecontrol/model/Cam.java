package com.cs1119it.fanxin.lecontrol.model;

import java.io.Serializable;

/**
 * Created by fanxin on 2017/3/22.
 */

public class Cam extends LecModel implements Serializable {
    public static Integer[] singleCamTypes = {0, 10, 20, 21, 40, 41, 50, 51, 60};
    public static Integer[] curtainCamTypes = {30, 31, 32, 33, 34};
    public static Integer[] airConditioningSpeedCamTypes = {72, 46, 47, 48, 76};
    public static Integer[] airConditioningModeCamTypes = {42, 43, 44, 45};
    public static Integer[] freshAirSpeedCamTypes = {61, 62, 63};

    Integer deviceId;
    Integer camId;
    Integer iType;
    Integer controlType;
    String controlAddress;
    String statusAddress;

    Integer controlValue;
    boolean checked;

    public String getControlAddress() {
        return controlAddress;
    }

    public void setControlAddress(String controlAddress) {
        this.controlAddress = controlAddress;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getCamId() {
        return camId;
    }

    public void setCamId(Integer camId) {
        this.camId = camId;
    }

    public Cam() {
        this.name = "";
        this.imageName = "";
        this.iType = 0;
        this.controlType = 0;
        this.controlAddress = "0/0/0";
        this.controlValue = 0;
    }

    public Cam(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
        this.iType = 0;
        this.controlType = 0;
        this.controlAddress = "0/0/0";
        this.controlValue = 0;
    }

    public Integer getiType() {
        return iType;
    }

    public void setiType(Integer iType) {
        this.iType = iType;
    }

    public Integer getControlValue() {
        return controlValue;
    }

    public void setControlValue(Integer controlValue) {
        this.controlValue = controlValue;
    }

    public Integer getControlType() {
        return controlType;
    }

    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }

    public String getStatusAddress() {
        return statusAddress;
    }

    public void setStatusAddress(String statusAddress) {
        this.statusAddress = statusAddress;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
