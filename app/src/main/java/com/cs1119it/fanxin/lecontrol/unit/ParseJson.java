package com.cs1119it.fanxin.lecontrol.unit;

import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Building;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.model.Floor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/23.
 */

public class ParseJson {
    public static Building parseBuildingDetail(StringBuilder stringBuilder) {
        Building building = new Building();

        try {
            JSONObject buildingOb = new JSONObject(stringBuilder.toString());
            building.setName(buildingOb.getString("name"));
            building.setSocketAddress(buildingOb.getString("socket_address"));
            building.setSocketPort(buildingOb.getInt("socket_port"));

            JSONArray floorArray = buildingOb.getJSONArray("floors");
            List<Floor> floors = new ArrayList<>();
            for (int i = 0; i < floorArray.length(); i++) {
                JSONObject floorOb = floorArray.getJSONObject(i);
                Floor floor = new Floor();
                floor.setFloorId(floorOb.getInt("id"));
                floor.setName(floorOb.getString("name"));
                JSONArray areaArray = floorOb.getJSONArray("areas");
                List<Area> areas = new ArrayList<>();
                for (int j = 0; j < areaArray.length(); j++) {
                    JSONObject areaOb = areaArray.getJSONObject(j);
                    Area area = new Area();
                    area.setFloorId(areaOb.getInt("floor_id"));
                    area.setAreaId(areaOb.getInt("id"));
                    area.setName(areaOb.getString("name"));
                    area.setImageName(areaOb.getString("image_name"));
                    JSONArray deviceArray = areaOb.getJSONArray("devices");
                    List<Device> devices = new ArrayList<>();
                    for (int k = 0; k < deviceArray.length(); k++) {
                        JSONObject deviceOb = deviceArray.getJSONObject(k);
                        Device device = new Device();
                        device.setName(deviceOb.getString("name"));
                        device.setiType(deviceOb.getInt("i_type"));
                        JSONArray camArray = deviceOb.getJSONArray("cams");
                        List<Cam> cams = new ArrayList<>();
                        for (int l = 0; l < camArray.length(); l++) {
                            JSONObject camOb = camArray.getJSONObject(l);
                            Cam cam = new Cam();
                            cam.setName(camOb.getString("name"));
                            cam.setImageName(camOb.getString("image_name"));
                            cam.setiType(camOb.getInt("i_type"));
                            cam.setControlType(camOb.getInt("control_type"));
                            cam.setControlAddress(camOb.getString("control_address"));
                            cam.setControlValue(camOb.getInt("control_value"));
                            cams.add(cam);
                        }
                        device.setCams(cams);
                        devices.add(device);
                    }
                    area.setDevices(devices);
                    areas.add(area);
                }
                floor.setAreas(areas);
                floors.add(floor);
            }
            building.setFloors(floors);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return building;
    }
}
