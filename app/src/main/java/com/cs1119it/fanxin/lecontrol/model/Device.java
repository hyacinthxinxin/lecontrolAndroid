package com.cs1119it.fanxin.lecontrol.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

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


    public List<Cam> getCamsIn(Integer[] camTypes){
        List<Cam> cams = new ArrayList<>();
        for (Cam cam : this.cams) {
            if (asList(camTypes).contains(cam.getiType())){
                cams.add(cam);
            }
        }
        return cams;
    }

    public Cam getCamByCamType(Integer camType) {
        for (Cam cam : getCams()) {
            if (cam.getiType().equals(camType)) {
                return cam;
            }
        }
        return null;
    }

    public Cam getCamByCamName(String camName) {
        for (Cam cam : getCams()) {
            if (cam.getName().equals(camName)) {
                return cam;
            }
        }
        return null;
    }

    public Cam getCamByStatusAddress(String statusAddress) {
        for (Cam cam : getCams()) {
            if (cam.getStatusAddress().equals(statusAddress)) {
                return cam;
            }
        }
        return null;
    }

    public Cam getCamByStatusAddressAndValue(String statusAddress, Integer value) {
        for (Cam cam : getCams()) {
            if (cam.getStatusAddress().equals(statusAddress) && cam.getControlValue().equals(value)) {
                return cam;
            }
        }
        return null;
    }

}
