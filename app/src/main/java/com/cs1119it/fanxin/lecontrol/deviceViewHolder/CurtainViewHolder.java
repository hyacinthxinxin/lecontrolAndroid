package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/25.
 */

public class CurtainViewHolder extends BaseDeviceViewHolder {
    //开 30
    private View view1;
    private TextView textView1;
    private ImageButton imageButton1;
    //关 31
    private View view2;
    private TextView textView2;
    private ImageButton imageButton2;
    //升 32
    private View view3;
    private TextView textView3;
    private ImageButton imageButton3;
    //降 33
    private View view4;
    private TextView textView4;
    private ImageButton imageButton4;
    //停 34
    private View view5;
    private TextView textView5;
    private ImageButton imageButton5;

    @Override
    public void setDevice(Device device) {
        super.setDevice(device);
        List<Cam> cams = this.device.getCams();
        List<Integer> availableSenders = new ArrayList<>();
        for (final Cam cam : cams) {
            availableSenders.add(cam.getiType());
            ControlSenderListener controlSenderListener = new ControlSenderListener(cam);
            switch (cam.getiType()) {
                case 30:
                    textView1.setText(cam.getName());
                    imageButton1.setOnClickListener(controlSenderListener);
                    break;
                case 31:
                    textView2.setText(cam.getName());
                    imageButton2.setOnClickListener(controlSenderListener);
                    break;
                case 32:
                    textView3.setText(cam.getName());
                    imageButton3.setOnClickListener(controlSenderListener);
                    break;
                case 33:
                    textView4.setText(cam.getName());
                    imageButton4.setOnClickListener(controlSenderListener);
                    break;
                case 34:
                    textView5.setText(cam.getName());
                    imageButton5.setOnClickListener(controlSenderListener);
                    break;
                default:
                    break;
            }
        }
        if (!availableSenders.contains(30))
            view1.setVisibility(View.GONE);
        if (!availableSenders.contains(31))
            view2.setVisibility(View.GONE);
        if (!availableSenders.contains(32))
            view3.setVisibility(View.GONE);
        if (!availableSenders.contains(33))
            view4.setVisibility(View.GONE);
        if (!availableSenders.contains(34))
            view5.setVisibility(View.GONE);
    }

    private class ControlSenderListener implements View.OnClickListener {
        private Cam cam;
        ControlSenderListener(Cam cam) {
            super();
            this.cam = cam;
        }
        @Override
        public void onClick(View v) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    LeControlCode leControlCode = new LeControlCode(cam.getControlAddress(), cam.getControlType(), cam.getControlValue());
                    SocketManager.sharedSocket().sendMsg(leControlCode.message(true));
                }
            }.start();
        }
    }

    public CurtainViewHolder(View itemView) {
        super(itemView);
        view1 = itemView.findViewById(R.id.device_curtain_sender1);
        textView1 = (TextView) view1.findViewById(R.id.cam_button_sender1_textView);
        imageButton1 = (ImageButton) view1.findViewById(R.id.cam_button_sender1_imageButton);
        view2 = itemView.findViewById(R.id.device_curtain_sender2);
        textView2 = (TextView) view2.findViewById(R.id.cam_button_sender1_textView);
        imageButton2 = (ImageButton) view2.findViewById(R.id.cam_button_sender1_imageButton);
        view3 = itemView.findViewById(R.id.device_curtain_sender3);
        textView3 = (TextView) view3.findViewById(R.id.cam_button_sender1_textView);
        imageButton3 = (ImageButton) view3.findViewById(R.id.cam_button_sender1_imageButton);
        view4 = itemView.findViewById(R.id.device_curtain_sender4);
        textView4 = (TextView) view4.findViewById(R.id.cam_button_sender1_textView);
        imageButton4 = (ImageButton) view4.findViewById(R.id.cam_button_sender1_imageButton);
        view5 = itemView.findViewById(R.id.device_curtain_sender5);
        textView5 = (TextView) view5.findViewById(R.id.cam_button_sender1_textView);
        imageButton5 = (ImageButton) view5.findViewById(R.id.cam_button_sender1_imageButton);
    }

}
