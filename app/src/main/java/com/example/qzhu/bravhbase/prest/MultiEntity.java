package com.example.qzhu.bravhbase.prest;

/**
 * Created by Qzhu on 2017/8/30.
 */

public class MultiEntity implements ItemType{
    private int itemType;

    public MultiEntity(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType%3+3;
    }
}
