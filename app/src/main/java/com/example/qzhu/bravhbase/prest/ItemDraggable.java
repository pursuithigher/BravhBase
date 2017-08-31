package com.example.qzhu.bravhbase.prest;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Qzhu on 2017/8/30.
 */

public interface ItemDraggable {
    boolean isItemSwipeEnable();
    void onItemDragStart(RecyclerView.ViewHolder viewHolder);
    void onItemDragEnd(RecyclerView.ViewHolder viewHolder);
    void onItemSwipeClear(RecyclerView.ViewHolder viewHolder);
    void onItemDragMoving(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target);
    void onItemSwiped(RecyclerView.ViewHolder viewHolder);
    void onItemSwiping(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive);
    void onItemSwipeStart(RecyclerView.ViewHolder viewHolder);
}
