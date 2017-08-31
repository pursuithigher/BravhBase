package com.example.qzhu.bravhbase.prest;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.qzhu.bravhbase.R;

import java.util.List;

/**
 * Created by Qzhu on 2017/8/30.
 */

public class MultiAdapter<T extends ItemType,VH extends MyViewHolder> extends BackQuickAdapter<T,VH>{

    public MultiAdapter(List<T> datas) {
        super(datas);
    }

    @Override
    View onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);
    }
    @Override
    void onBindDefViewHolder(VH holder, int realDataPos) {
        View contentView = holder.itemView;
        int type = datas.get(realDataPos).getItemType();
        String args = "数据值："+String.valueOf(realDataPos);
        if(type == GroupEntity.GROUPHEADER) {
            ((TextView) contentView.findViewById(R.id.recycler_list_txt)).setTextColor(Color.RED);
        }
        ((TextView) contentView.findViewById(R.id.recycler_list_txt)).setText(args);
    }
    @Override
    protected int getDefItemViewType(int realDataPos) {
        return datas.get(realDataPos).getItemType();
    }
}
