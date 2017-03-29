package com.cs1119it.fanxin.lecontrol.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/22.
 */

public class Device extends LecModel implements Serializable {
    Integer iType;
    List<Cam> cams;

    Integer areaId;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    Integer deviceId;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Device() {
        this.name = "";
        this.imageName = "";
    }

    public Device(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
    }

    public Integer getiType() {
        return iType;
    }

    public void setiType(Integer iType) {
        this.iType = iType;
    }

    public List<Cam> getCams() {
        return cams;
    }

    public void setCams(List<Cam> cams) {
        this.cams = cams;
    }

    public Cam getCamByCamType(Integer camType) {
        for (Cam cam: getCams()) {
            if (cam.getiType().equals(camType)) {
                return cam;
            }
        }
        return null;
    }

    public Cam getCamByCamName(String camName) {
        for (Cam cam: getCams()) {
            if (cam.getName().equals(camName)) {
                return cam;
            }
        }
        return null;
    }

}
