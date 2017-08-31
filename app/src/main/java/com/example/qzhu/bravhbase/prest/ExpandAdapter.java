package com.example.qzhu.bravhbase.prest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.qzhu.bravhbase.R;

import java.util.List;

/**
 * Created by Qzhu on 2017/8/30.
 */
public class ExpandAdapter<I extends Expandable<I>,VH extends MyViewHolder> extends BackQuickAdapter<I,VH> {

    public ExpandAdapter(List<I> datas) {
        super(datas);
    }

    @Override
    View onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);
    }

    @Override
    void onBindDefViewHolder(VH holder, int realDataPos) {
        ExpandEntity entity = (ExpandEntity) datas.get(realDataPos);
        ((TextView)holder.itemView.findViewById(R.id.recycler_list_txt)).setText(entity.getValues());
        holder.itemView.setTag(datas.get(realDataPos));
        holder.itemView.setOnClickListener(new ClickDelegate(realDataPos));
    }

    private class ClickDelegate implements View.OnClickListener{
        //当前位置之后插入+headercount
        private int position = 0;

        public ClickDelegate(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            Expandable<I> raw = (Expandable<I>) v.getTag();
            if(raw.isExpandable() && !raw.isExpand()){
                //toggle this subItem
                //expand or compose
                datas.addAll(position+1,raw.subItems());
                //from start +2 to datas.size count number
                notifyItemRangeInserted(position + 1 + 1, raw.subItems().size());
                raw.setExpand(true);
                Log.i("itemclick","expand");
            }else if(raw.isExpand()){
                //这里需要折叠，去除datas中间的数据，多级遍历删除所有当前项的子项
                Log.i("itemclick","compose");
            }
        }
    }

    @Override
    protected int getDefItemViewType(int realDataPos) {
        return datas.get(realDataPos).getItemType();
    }
}
