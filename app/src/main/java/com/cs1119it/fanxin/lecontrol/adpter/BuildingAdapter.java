package com.cs1119it.fanxin.lecontrol.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs1119it.fanxin.lecontrol.R;
import com.cs1119it.fanxin.lecontrol.model.DeviceGroupType;
import com.cs1119it.fanxin.lecontrol.model.SimpleBuilding;
import com.cs1119it.fanxin.lecontrol.unit.Constant;

import java.util.List;

/**
 * Created by fanxin on 2017/3/29.
 */

public class BuildingAdapter extends RecyclerView.Adapter {
    public interface OnBuildingChoose {
        void onBuildingClick(int position);
    }

    private BuildingAdapter.OnBuildingChoose onBuildingChoose;

    public void setOnBuildingChoose(OnBuildingChoose onBuildingChoose) {
        this.onBuildingChoose = onBuildingChoose;
    }

    private List<SimpleBuilding> simpleBuildings;

    public BuildingAdapter(List<SimpleBuilding> simpleBuildings) {
        this.simpleBuildings = simpleBuildings;
    }

    private class BuildingViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private int position;
        private TextView buildingNameTv;

        private BuildingViewHolder(View itemView) {
            super(itemView);
            buildingNameTv = (TextView) itemView.findViewById(R.id.building_name_textView);
            rootView = itemView.findViewById(R.id.item_building);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onBuildingChoose) {
                        onBuildingChoose.onBuildingClick(position);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_building, null);
        return new BuildingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BuildingViewHolder h = (BuildingViewHolder) holder;
        h.position = h.getAdapterPosition();
        String name = simpleBuildings.get(position).getName();
        h.buildingNameTv.setText(name);
    }

    @Override
    public int getItemCount() {
        return simpleBuildings.size();
    }

}
