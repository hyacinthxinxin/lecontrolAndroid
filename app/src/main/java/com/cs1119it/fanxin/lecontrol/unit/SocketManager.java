package com.cs1119it.fanxin.lecontrol.unit;

import android.util.Log;

import com.cs1119it.fanxin.lecontrol.FloorAndAreaActivity;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Building;
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
                    JSONArray deviceArray = areaOb.getJSONArray("devices");
                    List<Device> devices = new ArrayList<>();
                    for (int k=0; k<deviceArray.length(); k++) {
                        JSONObject deviceOb = deviceArray.getJSONObject(k);
                        Device device = new Device();
                        device.setName(deviceOb.getString("name"));
                        device.setiType(deviceOb.getInt("i_type"));
                        devices.add(device);
                    }
                    area.setDevices(devices);
                    areas.add(area);
                }
                floor.setAreas(areas);
                floors.add(floor);
            }
            building.setFloors(floors);
            Log.d("Model","json parse complete");
            Log.d("Model",building.getSocketAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return building;
    }

    private Floor getFloor(Integer floorId) {
        for (Floor floor: building.getFloors()) {
            if  (floor.getFloorId() == floorId) {
                return floor;
            }
        }
        return new Floor();
    }

    public Area getArea(Integer floorId, Integer areaId) {
        Floor floor = getFloor(floorId);
        for (Area area: floor.getAreas()) {
            if  (area.getAreaId() == areaId) {
                return area;
            }
        }
        return new Area();
    }
}
