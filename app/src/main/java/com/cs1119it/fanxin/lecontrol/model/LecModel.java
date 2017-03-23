package com.cs1119it.fanxin.lecontrol.model;

import java.io.Serializable;

/**
 * Created by fanxin on 2017/3/22.
 */

public class LecModel implements Serializable {
    String name;
    String imageName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
