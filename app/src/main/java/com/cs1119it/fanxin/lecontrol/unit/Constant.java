package com.cs1119it.fanxin.lecontrol.unit;

import com.cs1119it.fanxin.lecontrol.R;

/**
 * Created by fanxin on 2017/3/23.
 */

public class Constant {
    public static int getAreaImage(String name){
        switch (name){
            case "bar-counter":
                return R.drawable.room_icon_chess_cards;
            case "chess-cards":
                return R.drawable.room_icon_study;
            case "children":
                return R.drawable.room_icon_hallway;
            case "conference":
                return R.drawable.room_icon_children;
            case "dining":
                return R.drawable.room_icon_dining;
            case "guest-rest":
                return R.drawable.room_icon_guest_rest;
            case "hallway":
                return R.drawable.room_icon_hallway;
            case "kitchen":
                return R.drawable.room_icon_kitchen;
            case "living":
                return R.drawable.room_icon_living;
            case "main-bath":
                return R.drawable.room_icon_main_bath;
            case "main-bed":
                return R.drawable.room_icon_main_bed;
            case "old":
                return R.drawable.room_icon_old;
            case "reading":
                return R.drawable.room_icon_reading;
            case "study":
                return R.drawable.room_icon_study;
            case "the-porch":
                return R.drawable.room_icon_the_porch;
            case "video":
                return R.drawable.room_icon_video;
            case "winter-garden":
                return R.drawable.room_icon_winter_garden;
            default:
                return R.drawable.room_icon_other;
        }
    }

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

    public static int getSceneImage(String name, boolean selected) {
        switch (name) {
            case "all_close":
                return selected? R.drawable.mode_all_close_sel: R.drawable.mode_all_close_nor;
            case "all_open":
                return selected? R.drawable.mode_all_open_sel: R.drawable.mode_all_open_nor;
            case "amuse":
                return selected? R.drawable.mode_amuse_sel: R.drawable.mode_amuse_nor;
            case "back_home":
                return selected? R.drawable.mode_back_home_sel: R.drawable.mode_back_home_nor;
            case "bath":
                return selected? R.drawable.mode_bath_sel: R.drawable.mode_bath_nor;
            case "chinese_food":
                return selected? R.drawable.mode_chinese_food_sel: R.drawable.mode_chinese_food_nor;
            case "leave_home":
                return selected? R.drawable.mode_leave_home_sel: R.drawable.mode_leave_home_nor;
            case "meal_preparation":
                return selected? R.drawable.mode_meal_preparation_sel: R.drawable.mode_meal_preparation_nor;
            case "morning":
                return selected? R.drawable.mode_morning_sel: R.drawable.mode_morning_nor;
            case "night":
                return selected? R.drawable.mode_night_sel: R.drawable.mode_night_nor;
            case "read":
                return selected? R.drawable.mode_read_sel: R.drawable.mode_read_nor;
            case "receive":
                return selected? R.drawable.mode_receive_sel: R.drawable.mode_receive_nor;
            case "rest":
                return selected? R.drawable.mode_rest_sel: R.drawable.mode_rest_nor;
            case "study":
                return selected? R.drawable.mode_study_sel: R.drawable.mode_study_nor;
            case "video":
                return selected? R.drawable.mode_video_sel: R.drawable.mode_video_nor;
            case "wash":
                return selected? R.drawable.mode_wash_sel: R.drawable.mode_wash_nor;
            case "western":
                return selected? R.drawable.mode_western_sel: R.drawable.mode_western_nor;
            default:
                return selected? R.drawable.mode_other_sel: R.drawable.mode_other_nor;
        }
    }
}
