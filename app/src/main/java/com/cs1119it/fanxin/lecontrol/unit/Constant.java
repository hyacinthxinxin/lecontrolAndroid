package com.cs1119it.fanxin.lecontrol.unit;

import com.cs1119it.fanxin.lecontrol.R;

/**
 * Created by fanxin on 2017/3/23.
 */

public class Constant {
    public static int getDeviceTypeImage(String name) {
        switch (name){
            case "device_group_scene":
                return R.drawable.device_group_scene;
            case "device_group_light":
                return R.drawable.device_group_light;
            case "device_group_curtain":
                return R.drawable.device_group_curtain;
            case "device_group_temperature":
                return R.drawable.device_group_temperature;
            default:
                return 0;
        }
    }

    public static int getImage(String name){
        //设置头像
        switch (name){
            case "chess-cards":
                return R.mipmap.chess_cards_room;
            case "study":
                return R.mipmap.study_room;
            case "hallway":
                return R.mipmap.hallway_room;
            case "children":
                return R.mipmap.children_room;
            case "dining":
                return R.mipmap.dining_room;
            case "bar-counter":
                return R.mipmap.bar_counter_room;
            case "conference":
                return R.mipmap.conference_room;
            case "guest-rest":
                return R.mipmap.guest_rest_room;
            case "kitchen":
                return R.mipmap.kitchen_room;
            case "main-bath":
                return R.mipmap.main_bath_room;
            case "main-bde":
                return R.mipmap.main_bed_room;
            case "old":
                return R.mipmap.old_room;
            case "reading":
                return R.mipmap.reading_room;
            case "the-porch":
                return R.mipmap.the_porch_room;
            case "winter-garden":
                return R.mipmap.wind_bigger;
            case "other_room":
            default:
                return R.mipmap.other_room;
        }
    }
}
