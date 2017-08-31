package com.example.qzhu.bravhbase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.qzhu.bravhbase.prest.BackQuickAdapter;
import com.example.qzhu.bravhbase.prest.ExpandAdapter;
import com.example.qzhu.bravhbase.prest.ExpandEntity;
import com.example.qzhu.bravhbase.prest.GroupEntity;
import com.example.qzhu.bravhbase.prest.ItemDragSwipeCallback;
import com.example.qzhu.bravhbase.prest.MultiAdapter;
import com.example.qzhu.bravhbase.prest.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager linerLayout;
    private BackQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.activity_recycleview);
        //BaseQuickAdapter

        initial2();
    }

    private void initial(){
        linerLayout = new LinearLayoutManager(this);
        List<GroupEntity> datas = new ArrayList<>();
        for(int i = 0 ;i < 50 ;i ++)
        {
            datas.add(new GroupEntity(i%5 == 0, String.valueOf(i)));
        }
        adapter = new MultiAdapter<GroupEntity,MyViewHolder>(datas);
        adapter.addFooterView(R.layout.recycler_footer);
        adapter.addHeaderView(R.layout.recycler_header);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linerLayout);
        addItemTouchHelper();
    }

    private void addItemTouchHelper(){
        ItemDragSwipeCallback itemDragSwipeCallback = new ItemDragSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initial2(){
        linerLayout = new LinearLayoutManager(this);
        //sub1
        List<ExpandEntity> datas = new ArrayList<>();

        List<ExpandEntity> datas2 = new ArrayList<>();
        for(int i = 100 ;i <115 ;i++)
        {
            datas2.add(new ExpandEntity(ExpandEntity.SUB2, null,String.valueOf(i)));
        }
        datas.add(new ExpandEntity(ExpandEntity.SUB1, datas2,"0"));

        datas.add(new ExpandEntity(ExpandEntity.SUB1, datas2,"1"));

        List<ExpandEntity> datas3 = new ArrayList<>();
        datas.add(new ExpandEntity(ExpandEntity.SUB2, datas3,"2"));

        adapter = new ExpandAdapter<ExpandEntity,MyViewHolder>(datas);

        adapter.addFooterView(R.layout.recycler_footer);
        adapter.addHeaderView(R.layout.recycler_header);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linerLayout);
        addItemTouchHelper();
    }
}
