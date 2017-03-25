package com.cs1119it.fanxin.lecontrol.model;

/**
 * Created by fanxin on 2017/3/23.
 */

public class DeviceGroupType {
    Integer type;
    String name;
    String imageName;

    public DeviceGroupType(Integer type,String name, String imageName) {
        this.type = type;
        this.name = name;
        this.imageName = imageName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
