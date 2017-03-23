package com.cs1119it.fanxin.lecontrol.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/22.
 */

public class Building extends LecModel implements Serializable {
    Integer userId, buildingId;
    String socketAddress;
    Integer socketPort;
    List<Floor> floors;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(String socketAddress) {
        this.socketAddress = socketAddress;
    }

    public Integer getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(Integer socketPort) {
        this.socketPort = socketPort;
    }

    public List<Floor> getFloors() {
        if (null == floors) {
            return new ArrayList<>();
        }
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }
}
