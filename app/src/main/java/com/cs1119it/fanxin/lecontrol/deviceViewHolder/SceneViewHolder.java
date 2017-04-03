package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.adpter.AreaDetailAdapter;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.unit.Constant;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

/**
 * Created by fanxin on 2017/3/25.
 */


public class SceneViewHolder extends BaseDeviceViewHolder {
    public interface OnSceneChoose {
        void onSceneChoose(int position);
    }
    private OnSceneChoose onSceneChoose;

    public void setOnSceneChoose(OnSceneChoose onSceneChoose) {
        this.onSceneChoose = onSceneChoose;
    }

    public View rootView;
    public int position;
    private TextView sceneNameTv;
    private ImageView sceneImageIv;

    public SceneViewHolder(View itemView) {
        super(itemView);
        rootView = itemView.findViewById(R.id.scene_send);
        sceneNameTv = (TextView) itemView.findViewById(R.id.scene_textView);
        sceneImageIv = (ImageView) itemView.findViewById(R.id.scene_imageView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSceneChoose != null) {
                    onSceneChoose.onSceneChoose(position);
                }
            }
        });
    }

    @Override
    public void setDevice(final Device device) {
        super.setDevice(device);
        setupView();
    }

    private void setupView() {
        sceneNameTv.setText(device.getName());
        sceneImageIv.setImageResource(Constant.getSceneImage(device.getImageName(), device.isChecked()));
    }

}
