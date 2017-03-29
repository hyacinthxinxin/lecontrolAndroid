package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

/**
 * Created by fanxin on 2017/3/25.
 */


public class SceneViewHolder extends BaseDeviceViewHolder {
    private TextView sceneNameTv;
    private ImageButton sceneSenderIb;

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        this.device = device;
        sceneNameTv.setText(device.getName());
        sceneSenderIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        for (Cam cam: device.getCams()) {
                            LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), cam.getControlValue());
                            SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                        }
                    }
                }.start();
            }
        });
    }

    public SceneViewHolder(View itemView) {
        super(itemView);
        sceneNameTv = (TextView) itemView.findViewById(R.id.scene_textView);
        sceneSenderIb = (ImageButton) itemView.findViewById(R.id.scene_imageButton);
    }

}
