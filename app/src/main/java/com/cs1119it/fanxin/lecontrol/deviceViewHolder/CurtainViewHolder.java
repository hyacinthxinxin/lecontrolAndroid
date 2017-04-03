package com.cs1119it.fanxin.lecontrol.deviceViewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Cam;
import com.cs1119it.fanxin.lecontrol.model.Device;
import com.cs1119it.fanxin.lecontrol.unit.Constant;
import com.cs1119it.fanxin.lecontrol.unit.LeControlCode;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

/**
 * Created by fanxin on 2017/3/25.
 */

public class CurtainViewHolder extends BaseDeviceViewHolder {
    private TextView curtainNameTv;

    private List<ImageButton> imageButtonList;
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
        curtainNameTv.setText(device.getName());
        List<Cam> cams = this.device.getCams();
        for (final Cam cam : cams) {
            ControlSenderListener controlSenderListener = new ControlSenderListener(cam);
            switch (cam.getiType()) {
                case 30:
                    view1.setVisibility(VISIBLE);
                    textView1.setText(cam.getName());
                    imageButton1.setOnClickListener(controlSenderListener);
                    imageButton1.setImageResource(cam.isChecked() ? R.drawable.curtain_open_click : R.drawable.curtain_open);
                    imageButtonList.add(imageButton1);
                    break;
                case 31:
                    view2.setVisibility(VISIBLE);
                    textView2.setText(cam.getName());
                    imageButton2.setOnClickListener(controlSenderListener);
                    imageButton2.setImageResource(cam.isChecked() ? R.drawable.curtain_close_click : R.drawable.curtain_close);
                    imageButtonList.add(imageButton2);
                    break;
                case 32:
                    view3.setVisibility(VISIBLE);
                    textView3.setText(cam.getName());
                    imageButton3.setOnClickListener(controlSenderListener);
                    imageButton3.setImageResource(cam.isChecked() ? R.drawable.curtain_up_click : R.drawable.curtain_up);
                    imageButtonList.add(imageButton3);
                    break;
                case 33:
                    view4.setVisibility(VISIBLE);
                    textView4.setText(cam.getName());
                    imageButton4.setOnClickListener(controlSenderListener);
                    imageButton4.setImageResource(cam.isChecked() ? R.drawable.curtain_down_click : R.drawable.curtain_down);
                    imageButtonList.add(imageButton4);
                    break;
                case 34:
                    view5.setVisibility(VISIBLE);
                    textView5.setText(cam.getName());
                    imageButton5.setOnClickListener(controlSenderListener);
                    imageButton5.setImageResource(cam.isChecked() ? R.drawable.curtain_pause_click : R.drawable.curtain_pause);
                    imageButtonList.add(imageButton5);
                    break;
                default:
                    break;
            }
        }
    }

    private class ControlSenderListener implements View.OnClickListener {
        private Cam cam;

        ControlSenderListener(Cam cam) {
            super();
            this.cam = cam;
        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < device.getCams().size(); i++) {
                ImageButton ib = (ImageButton) imageButtonList.get(i);
                Cam c = device.getCams().get(i);
                ib.setImageResource(getCurtainImageByCamType(c.getiType(), false));
            }

            ImageButton ib = (ImageButton) v;
            ib.setImageResource(getCurtainImageByCamType(cam.getiType(), true));

//            for (int i = 0; i < device.getCams().size(); i++) {
//                Cam c = device.getCams().get(i);
//                if (c.getCamId().equals(cam.getCamId())) {
//                    c.setChecked(true);
//                } else {
//                    c.setChecked(false);
//                }


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
        imageButtonList = new ArrayList<>();
        curtainNameTv = (TextView) itemView.findViewById(R.id.curtain_name_textView);
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

    private int getCurtainImageByCamType(Integer camType, boolean selected) {
        switch (camType) {
            case 30:
                return Constant.getCurtainImage("curtain_open", selected);
            case 31:
                return Constant.getCurtainImage("curtain_close", selected);
            case 32:
                return Constant.getCurtainImage("curtain_up", selected);
            case 33:
                return Constant.getCurtainImage("curtain_down", selected);
            case 34:
                return Constant.getCurtainImage("curtain_stop", selected);
            default:
                return 0;
        }
    }
}
