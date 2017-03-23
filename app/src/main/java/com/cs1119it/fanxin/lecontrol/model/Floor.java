package com.cs1119it.fanxin.lecontrol.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/22.
 */

public class Floor extends LecModel implements Serializable {
    Integer buildingId;

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    Integer floorId;

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    List<Area> areas;

    public List<Area> getAreas() {
        if (null == areas) {
            return new ArrayList<>();
        }
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

}
