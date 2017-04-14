package com.cs1119it.fanxin.lecontrol.unit;

import android.util.Log;

/**
 * Created by fanxin on 2017/3/25.
 */

public class LeControlCode {
    public static String code = "7a4000010001005a";

    private String star,type_write,type_read,mainAd,middleAd,dataType,value,status,lrc,end;

    public LeControlCode(String address, Integer dataType, Integer value) {

        star="7a";
        type_write="40";
        type_read="41";

        String[] ads=address.split("/");
        Integer first = Integer.parseInt(ads[0]);
        Integer second = Integer.parseInt(ads[1]);
        Integer third = Integer.parseInt(ads[2]);

        mainAd=String.format("%1s", Integer.toHexString(first))+String.format("%1s", Integer.toHexString(second));
        middleAd=String.format("%2s", Integer.toHexString(third)).replace(' ', '0');

        this.value = String.format("%2s", Integer.toHexString(value)).replace(' ', '0');

        if (dataType.equals(0)) {
            this.dataType="00";
        } else if (dataType.equals(1)) {
            this.dataType="01";
        } else {
            this.dataType="02";
        }
        lrc="00";
        end="5a";
    }


    public String message(boolean type){
        if(type) {
            Integer integerLrc = ~(Integer.parseInt(star, 16) + Integer.parseInt(type_write, 16)
                    + Integer.parseInt(mainAd, 16) + Integer.parseInt(middleAd, 16)
                    + Integer.parseInt(dataType, 16) + Integer.parseInt(value, 16)) & 0x00ff;
            integerLrc += 1;
            lrc = String.format("%2s", Integer.toHexString(integerLrc)).replace(' ', '0');
            if (dataType.equals("02")) {
                return star + type_write + mainAd + middleAd + dataType + value + "00" + lrc + end;
            }
            return star + type_write + mainAd + middleAd + dataType + value + lrc + end;
        } else {
            Integer integerLrc = ~(Integer.parseInt(star, 16) + Integer.parseInt(type_read, 16)
                    + Integer.parseInt(mainAd, 16) + Integer.parseInt(middleAd, 16)
                    + Integer.parseInt(dataType, 16)) & 0x00ff;
            integerLrc += 1;
            lrc = String.format("%2s", Integer.toHexString(integerLrc)).replace(' ', '0');
            return star+type_read+mainAd+middleAd+dataType+lrc+end;
        }
    }

    public String get_status(){
        return star+type_read+mainAd+middleAd+dataType+lrc+end;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getMainAd() {
        return mainAd;
    }

    public void setMainAd(String mainAd) {
        this.mainAd = mainAd;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMiddleAd() {
        return middleAd;
    }

    public void setMiddleAd(String middleAd) {
        this.middleAd = middleAd;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
