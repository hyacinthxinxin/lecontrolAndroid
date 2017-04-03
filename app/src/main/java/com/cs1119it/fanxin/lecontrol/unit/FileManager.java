package com.cs1119it.fanxin.lecontrol.unit;

import android.os.Environment;

/**
 * Created by fanxin on 2017/3/31.
 */

public class FileManager {
    public String getInnerSDCardPath() {
        return isSdCardExist()? Environment.getExternalStorageDirectory().getPath(): "NoSDCard";
    }

    public String getDataPath() {
        return Environment.getDataDirectory().getPath();
    }

    private static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
