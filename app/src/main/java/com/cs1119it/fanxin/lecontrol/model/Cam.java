package com.cs1119it.fanxin.lecontrol.model;

import java.io.Serializable;

/**
 * Created by fanxin on 2017/3/22.
 */

public class Cam extends LecModel implements Serializable {
    Integer deviceId;
    Integer camId;
    Integer iType;
    Integer controlType;
    String controlAddress;
    Integer controlValue;

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
}
