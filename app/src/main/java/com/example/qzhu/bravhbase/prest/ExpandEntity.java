package com.example.qzhu.bravhbase.prest;

import java.util.List;

/**
 * Created by Qzhu on 2017/8/30.
 */

public class ExpandEntity implements Expandable<ExpandEntity>{

    public final static int SUB1 = 0x111;
    public final static int SUB2 = 0x112;
//    public final static int SUB3 = 0x113;

    private int itemType;
    private List<ExpandEntity> subDatas;
    private String values;

    public ExpandEntity(int itemType, List<ExpandEntity> subDatas,String values) {
        this.itemType = itemType;
        this.subDatas = subDatas;
        this.values = values;
    }

    public String getValues() {
        return values;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public List<ExpandEntity> subItems() {
        return subDatas;
    }

    @Override
    public boolean isExpandable() {
        return subDatas != null && subDatas.size() > 0;
    }

    boolean isExpand = false;
    @Override
    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    @Override
    public boolean isExpand() {
        return isExpand;
    }
}
