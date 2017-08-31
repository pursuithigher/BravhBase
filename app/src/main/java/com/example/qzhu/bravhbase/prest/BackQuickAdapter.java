package com.example.qzhu.bravhbase.prest;

import android.graphics.Canvas;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by Qzhu on 2017/8/29.
 */
public abstract class BackQuickAdapter<I,VH extends MyViewHolder> extends RecyclerView.Adapter<VH> implements ItemDraggable{

    protected List<I> datas;

    public BackQuickAdapter(List<I> datas) {
        this.datas = datas;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType)
        {
            case ITEM_TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(headerLayout, parent, false);
                break;
            case ITEM_TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(footerLayout, parent, false);
                break;
            default:
                view = onCreateDefViewHolder(parent,viewType);
        }
        //实例化VH对象
        return createViewHolder(view);
    }

    //be override
    abstract View onCreateDefViewHolder(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(VH holder, int position) {
        autoLoadMore(position);

        if(position < (hasHeader ? 1 : 0))
        {
            // this is header view
            return ;
        }
        int realDataPos = adjustPositionByType(position);
        if(realDataPos < datas.size())
        {
            onBindDefViewHolder(holder,realDataPos);
        }
        else{
            //this is footer View
        }
    }
    //be override
    abstract void onBindDefViewHolder(VH holder, int realDataPos);

    private void autoLoadMore(int position) {
        if(position == getItemCount() -1)
        {
            Log.i("position","current end is "+String.valueOf(position));
            //trigger loading more
        }
    }
    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size() + (hasFooter ? 1 : 0) + (hasHeader ? 1 : 0);
    }

    private int adjustPositionByType(int position){
        return position - (hasHeader ? 1 : 0);
    }


    public final static int ITEM_TYPE_HEADER = -1;
    private final static int ITEM_TYPE_NORMAL = 0;
    public final static int ITEM_TYPE_FOOTER = 1;
    @Override
    public int getItemViewType(int position) {
        int datasize = (datas == null ? 0 : datas.size());
        int headerCount = hasHeader ? 1 : 0;
        if(position < headerCount)
            return ITEM_TYPE_HEADER;
        int realIndex = position - headerCount;
        if(realIndex < datasize){
            return getDefItemViewType(realIndex);
        }
        else{
            return ITEM_TYPE_FOOTER;
        }
    }

    private boolean animEnabled = false;
    /**
     * 是否只需要一次item加载动画
     */
    private boolean AnimOnce = true;
    /**
     * 最新加载过动画的item的位置，用于比较下一次item的位置判断是否要加载
     */
    private int vaildPos = 0;
    @Override
    public void onViewAttachedToWindow(VH holder) {
        if(animEnabled)
        {
            int currentPos = holder.getAdapterPosition();
            if(AnimOnce)
                if(currentPos > vaildPos){
                    animateView(holder.itemView);
                    vaildPos = currentPos;
                }
                else{
                    animateView(holder.itemView);
                }
        }
        super.onViewAttachedToWindow(holder);
    }
    private void animateView(View root){
        root.setAlpha(0.3f);
        root.animate().alpha(1).setDuration(2500).start();
    }
    /**
     * 抽象类反射实例化
     * @param view
     * @return
     */
    protected VH createViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        VH k;
        // 泛型擦除会导致z为null
        if (z == null) {
            k = (VH) new MyViewHolder(view);
        } else {
            k = createGenericKInstance(z, view);
        }
        return k != null ? k : (VH) new MyViewHolder(view);
    }
    /**
     * try to create Generic K instance
     *
     * @param z
     * @param view
     * @return
     */
    @SuppressWarnings("unchecked")
    private VH createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * get generic parameter K
     *
     * @param z
     * @return
     */
    private static Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (MyViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }

    private int headerLayout;
    private int footerLayout;
    private boolean hasHeader = false;
    private boolean hasFooter = false;
    //added first before call setAdapter
    public void addHeaderView(@LayoutRes int headerLayout){
        this.headerLayout = headerLayout;
        hasHeader = true;
    }
    //added first before call setAdapter
    public void addFooterView(@LayoutRes int footerLayout){
        this.footerLayout = footerLayout;
        hasFooter = true;
    }

    @Override
    public boolean isItemSwipeEnable() {
        return true; //start swipe
    }

    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onItemSwipeClear(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        //change item position
        int from = getViewHolderPosition(source);
        int to = getViewHolderPosition(target);
        if (inRange(from) && inRange(to)) {
            if (from < to) {
                for (int i = from; i < to; i++) {
                    Collections.swap(datas, i, i + 1);
                }
            } else {
                for (int i = from; i > to; i--) {
                    Collections.swap(datas, i, i - 1);
                }
            }
            notifyItemMoved(source.getAdapterPosition(), target.getAdapterPosition());
        }
    }
    public int getViewHolderPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() - (hasHeader ? 1 : 0);
    }

    private boolean inRange(int position) {
        return position >= 0 && position < datas.size();
    }
    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        //notify item removed
        int position = getViewHolderPosition(viewHolder);
        if(position >= 0 && position < datas.size()) {
            datas.remove(position);
            notifyItemRemoved(getPositionInSets(position));
        }
    }
    public int getPositionInSets(int position) {
        return position + (hasHeader ? 1 : 0);
    }

    @Override
    public void onItemSwiping(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

    }

    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder) {

    }

    //multi item must be override
    protected int getDefItemViewType(int realDataPos) {
        //datas position item Type
        return ITEM_TYPE_NORMAL;
    }

}
