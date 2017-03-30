package com.cs1119it.fanxin.lecontrol.unit;

import android.os.Environment;
import android.util.Log;

import com.alibaba.fastjson.util.IOUtils;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Building;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.model.Floor;
import com.cs1119it.fanxin.lecontrol.service.ReceiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/23.
 */

public class SocketManager {
    public boolean needRefresh = false;
    private static Socket client = null;
    private String socket_address;
    private int socket_port;
    private ReceiveData receiveData;

    public boolean isNeedRefresh() {
        return needRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    //#1
    private volatile static SocketManager socketManager;

    //#2
    private SocketManager() {
        setBuildingDetail();
    }

    //#3
    public static SocketManager sharedSocket() {
        if (socketManager == null) {
            synchronized (SocketManager.class) {
                if (socketManager == null) {
                    socketManager = new SocketManager();
                }
            }
        }
        return socketManager;
    }

    public boolean reConnect() {
        try {
            socket_address = building.getSocketAddress();
            socket_port = building.getSocketPort();
            if (client == null) {
                client = new Socket(socket_address, socket_port);
            } else {
                client.connect(new InetSocketAddress(socket_address, socket_port));
            }
            Log.e("JAVA", "建立连接：" + client);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean isConnect() {
        return client != null && !client.isClosed() && client.isConnected();
    }

    public void setListener(ReceiveData receiveData) {
        this.receiveData = receiveData;
        while (isConnect()) {
            try {
                InputStream inputStream = client.getInputStream();
                byte[] bytes = new byte[inputStream.available()];
                int count = inputStream.read(bytes);
                if (count > 0) {
                    this.receiveData.receiveData(ByteStringUtil.byteArrayToHexStr(bytes));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public boolean sendMsg(final String str) {
        try {
            if (!isConnect()) {
                return false;
            }
            client.getOutputStream().write(ByteStringUtil.hexStrToByteArray(str));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setReceiveData(ReceiveData receiveData) {
        this.receiveData = receiveData;
    }

//    ***********************

    Building building;
    Area area;

    public Area getArea() {
        return area;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Building setBuildingDetail() {
        readConfigFromFile();

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
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        building = ParseJson.parseBuildingDetail(stringBuilder);
        return building;
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
