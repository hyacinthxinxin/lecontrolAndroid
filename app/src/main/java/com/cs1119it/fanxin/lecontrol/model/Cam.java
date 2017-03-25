package com.cs1119it.fanxin.lecontrol.model;

import java.io.Serializable;

/**
 * Created by fanxin on 2017/3/22.
 */

public class Cam extends LecModel implements Serializable {
    Integer iType;
    Integer deviceId;
    String controlAddress;

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

    Integer camId;

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
        this.controlAddress = "0/0/0";
    }

    public Cam(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
        this.iType = 0;
        this.controlAddress = "0/0/0";
    }

    public Integer getiType() {
        return iType;
    }

    public void setiType(Integer iType) {
        this.iType = iType;
    }
}
