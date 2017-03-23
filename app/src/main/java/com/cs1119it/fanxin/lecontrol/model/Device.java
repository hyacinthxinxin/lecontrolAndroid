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
        if (null == cams) {
            return new ArrayList<>();
        }
        return cams;
    }

    public void setCams(List<Cam> cams) {
        this.cams = cams;
    }
}
