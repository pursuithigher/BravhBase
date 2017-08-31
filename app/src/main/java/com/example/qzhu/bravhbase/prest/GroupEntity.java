package com.example.qzhu.bravhbase.prest;

/**
 * Created by Qzhu on 2017/8/30.
 */

public class GroupEntity implements ItemType{
    public final static int GROUPHEADER = 0X11;
    public final static int GROUPCONTENT = 0X12;

    private boolean isHeader = false;
    private String values;

    public GroupEntity(boolean isHeader, String values) {
        this.isHeader = isHeader;
        this.values = values;
    }
    @Override
    public int getItemType() {
        return isHeader ? GROUPHEADER : GROUPCONTENT;
    }
}
