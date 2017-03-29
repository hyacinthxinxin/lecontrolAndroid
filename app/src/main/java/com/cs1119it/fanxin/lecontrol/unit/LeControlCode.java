package com.cs1119it.fanxin.lecontrol.unit;

import android.util.Log;

/**
 * Created by fanxin on 2017/3/25.
 */

public class LeControlCode {
    public static String code = "7a4000010001005a";

    private String star,type_write,type_read,mainAd,middleAd,dataType,value,status,lrc,end;
    String len;

    public LeControlCode(String address, String dataType, Integer value) {
        String[] ads=address.split("/");
        for(int i=0;i<ads.length;i++){
            if(ads[2].length()==1){
                ads[2]="0"+ads[2];
            }
        }
        star="7a";
        type_write="40";
        type_read="41";
        mainAd=ads[0]+ads[1];
        middleAd=ads[2];
        this.dataType=dataType;
        this.value = String.format("%2s", Integer.toHexString(value)).replace(' ', '0');
        lrc="00";
        end="5a";
    }

    public LeControlCode(String address, Integer dataType, Integer value) {
        String[] ads=address.split("/");
        for(int i=0;i<ads.length;i++){
            if(ads[2].length()==1){
                ads[2]="0"+ads[2];
            }
        }
        star="7a";
        type_write="40";
        type_read="41";
        mainAd=ads[0]+ads[1];
        middleAd=ads[2];
        if (dataType.equals(0)) {
            this.dataType="00";
        } else if (dataType.equals(1)) {
            this.dataType="01";
        } else {
            this.dataType="02";
        }
        this.value = String.format("%2s", Integer.toHexString(value)).replace(' ', '0');
        lrc="00";
        end="5a";
    }


    public String message(boolean type){
        if(type) {
            return star + type_write + mainAd + middleAd + dataType + value + lrc + end;
        } else {
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
