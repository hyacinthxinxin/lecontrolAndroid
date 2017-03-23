package com.cs1119it.fanxin.lecontrol.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/21.
 */

public class Area extends LecModel implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    Integer floorId;

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    Integer areaId;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    Integer viewType;
    List<Device> devices;

    public Area() {
        this.name = "";
        this.imageName = "";
    }

    public Area(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
    }

    public Integer getViewType() {
        return viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
    }

    public List<Device> getDevices() {
        if (null == devices) {
            return new ArrayList<>();
        }
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

}
