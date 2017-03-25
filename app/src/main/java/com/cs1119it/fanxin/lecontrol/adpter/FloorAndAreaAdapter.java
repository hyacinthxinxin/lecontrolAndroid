package com.cs1119it.fanxin.lecontrol.adpter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.Area;
import com.cs1119it.fanxin.lecontrol.model.Building;
import com.cs1119it.fanxin.lecontrol.model.Floor;
import com.cs1119it.fanxin.lecontrol.unit.Constant;
import com.cs1119it.fanxin.lecontrol.unit.SocketManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanxin on 2017/3/21.
 */

public class FloorAndAreaAdapter extends RecyclerView.Adapter {

    public interface OnAreaClickListener {
        void onItemClick(int position);
    }

    private OnAreaClickListener onAreaClickListener;

    public void setOnAreaClickListener(OnAreaClickListener onAreaClickListener) {
        this.onAreaClickListener = onAreaClickListener;
    }

    private List<Area> list;
    public FloorAndAreaAdapter(List<Area> list) {
        this.list = list;
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_floor, null);
            return new FloorViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, null);
            return new AreaViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FloorViewHolder) {
            FloorViewHolder h = (FloorViewHolder) holder;
            h.position = h.getAdapterPosition();
            Area area = list.get(position);
            h.floorTv.setText(area.getName());
        } else {
            AreaViewHolder h = (AreaViewHolder) holder;
            h.position = h.getAdapterPosition();
            Area area = list.get(position);
            h.areaTv.setText(area.getName());
            h.areaIv.setImageResource(Constant.getAreaImage(area.getImageName()));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class AreaViewHolder extends RecyclerView.ViewHolder {
        private View areaItem;
        private TextView areaTv;
        private ImageView areaIv;
        private int position;

        private AreaViewHolder(View itemView){
            super(itemView);
            areaTv = (TextView) itemView.findViewById(R.id.area_name_textView);
            areaIv = (ImageView) itemView.findViewById(R.id.area_image_imageView);
            areaItem = itemView.findViewById(R.id.item_area);
            areaItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onAreaClickListener) {
                        onAreaClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    private class FloorViewHolder extends RecyclerView.ViewHolder {
        private TextView floorTv;
        private int position;

        private FloorViewHolder(View itemView) {
            super(itemView);
            floorTv = (TextView) itemView.findViewById(R.id.floor_name_textView);
        }
    }


}
