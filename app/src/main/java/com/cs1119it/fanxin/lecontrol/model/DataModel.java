package com.cs1119it.fanxin.lecontrol.model;

import android.os.Environment;

import com.cs1119it.fanxin.lecontrol.unit.ParseJson;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/23.
 */

public class DataModel {
    Building building;
    Area area;

    public Area getArea() {
        return area;
    }

    public DataModel() {
        super();
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Building setBuildingDetail() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/LeControl/");
        dir.mkdirs();
        File configFile = new File(dir, "config.json");
        if (configFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(configFile));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                building = ParseJson.parseBuildingDetail(stringBuilder);
                return building;
            } catch (IOException e) {
                e.printStackTrace();
                InputStream inputStream = DataModel.this.getClass().getClassLoader().getResourceAsStream("assets/" + "DefaultProject.json");
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    inputStream.close();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }

                building = ParseJson.parseBuildingDetail(stringBuilder);
                return building;
            }

        } else {
            InputStream inputStream = DataModel.this.getClass().getClassLoader().getResourceAsStream("assets/" + "DefaultProject.json");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            building = ParseJson.parseBuildingDetail(stringBuilder);
            return building;
        }
    }

    private void readConfigFromFile() {

    }

    private Floor getFloor(Integer floorId) {
        for (Floor floor : building.getFloors()) {
            if (floor.getFloorId().equals(floorId)) {
                return floor;
            }
        }
        return new Floor();
    }

    public Area getArea(Integer floorId, Integer areaId) {
        Floor floor = getFloor(floorId);
        for (Area area : floor.getAreas()) {
            if (area.getAreaId().equals(areaId)) {
                this.area = area;
                return area;
            }
        }
        return new Area();
    }

    public List<Device> getDevicesByDeviceGroupType(Integer deviceGroupType) {
        List<Device> devices = new ArrayList<>();
        for (int i = 0; i < area.getDevices().size(); i++) {
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
                    if (device.getiType() == 4 || device.getiType() == 5 || device.getiType() == 6 || device.getiType() == 7) {
                        devices.add(device);
                    }
                    break;
                default:
                    break;
            }
        }
        return devices;
    }

    public Cam getCamByStatusAddress(String statusAddress) {

        return null;
    }
}

