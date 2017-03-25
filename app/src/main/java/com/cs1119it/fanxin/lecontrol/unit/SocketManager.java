package com.cs1119it.fanxin.lecontrol.unit;

import android.util.Log;

import com.cs1119it.fanxin.lecontrol.FloorAndAreaActivity;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Building;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.model.Floor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/23.
 */

public class SocketManager {

    private SocketManager(){};
    public static SocketManager sharedSocket(){
        return SocketManagerHolder.sInstance;
    }
    /**
     * 静态内部类
     */
    private static class SocketManagerHolder{
        private static final SocketManager sInstance = new SocketManager();
    }

    Building building;
    Area area;

    public Area getArea() {
        return area;
    }

    public Building getBuilding() {
        return building;
    }


    public Building setBuildingDetail() {
        building = new Building();

        InputStream inputStream = SocketManager.this.getClass().getClassLoader().getResourceAsStream("assets/" + "DefaultProject.json");
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject buildingOb = new JSONObject(stringBuilder.toString());
            building.setName(buildingOb.getString("name"));
            building.setSocketAddress(buildingOb.getString("socket_address"));
            building.setSocketPort(buildingOb.getInt("socket_port"));

            JSONArray floorArray = buildingOb.getJSONArray("floors");
            List<Floor> floors = new ArrayList<>();
            for (int i=0; i<floorArray.length(); i++) {
                JSONObject floorOb = floorArray.getJSONObject(i);
                Floor floor = new Floor();
                floor.setFloorId(floorOb.getInt("id"));
                floor.setName(floorOb.getString("name"));
                JSONArray areaArray = floorOb.getJSONArray("areas");
                List<Area> areas = new ArrayList<>();
                for (int j=0; j<areaArray.length(); j++) {
                    JSONObject areaOb = areaArray.getJSONObject(j);
                    Area area = new Area();
                    area.setFloorId(areaOb.getInt("floor_id"));
                    area.setAreaId(areaOb.getInt("id"));
                    area.setName(areaOb.getString("name"));
                    area.setImageName(areaOb.getString("image_name"));
                    JSONArray deviceArray = areaOb.getJSONArray("devices");
                    List<Device> devices = new ArrayList<>();
                    for (int k=0; k<deviceArray.length(); k++) {
                        JSONObject deviceOb = deviceArray.getJSONObject(k);
                        Device device = new Device();
                        device.setName(deviceOb.getString("name"));
                        device.setiType(deviceOb.getInt("i_type"));
                        JSONArray camArray = deviceOb.getJSONArray("cams");
                        List<Cam> cams = new ArrayList<>();
                        for (int l=0; l<camArray.length(); l++) {
                            JSONObject camOb = camArray.getJSONObject(l);
                            Cam cam = new Cam();
                            cam.setiType(camOb.getInt("i_type"));
                            cam.setName(camOb.getString("name"));
                            cam.setControlAddress(camOb.getString("control_address"));
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

    private Floor getFloor(Integer floorId) {
        for (Floor floor: building.getFloors()) {
            if  (floor.getFloorId().equals(floorId)) {
                return floor;
            }
        }
        return new Floor();
    }

    public Area getArea(Integer floorId, Integer areaId) {
        Floor floor = getFloor(floorId);
        for (Area area: floor.getAreas()) {
            if  (area.getAreaId().equals(areaId)) {
                this.area = area;
                return area;
            }
        }
        return new Area();
    }

    public List<Device> getDevicesByDeviceGroupType(Integer deviceGroupType) {
        List<Device> devices = new ArrayList<>();
        for (int i=0; i<area.getDevices().size(); i++) {
            Device device = area.getDevices().get(i);
            switch (deviceGroupType) {
                case 0:
                    if (device.getiType() == 0) {
                        devices.add(device);
                    }
                    break;
                case 1:
                    if (device.getiType() == 1 || device.getiType() == 2) {
                        devices.add(device);
                    }
                    break;
                case 2:
                    if (device.getiType() == 3) {
                        devices.add(device);
                    }
                    break;
                case 3:
                    if (device.getiType() == 4 || device.getiType() == 5 || device.getiType() == 6) {
                        devices.add(device);
                    }
                    break;
                default:
                    break;
            }
        }
        return devices;
    }

}
