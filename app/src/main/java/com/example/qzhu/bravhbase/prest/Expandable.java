package com.example.qzhu.bravhbase.prest;

import java.util.List;

/**
 * Created by Qzhu on 2017/8/30.
 */

public interface Expandable<E> extends ItemType{
    List<E> subItems();
    boolean isExpandable();
    void setExpand(boolean expand);
    boolean isExpand();
}
