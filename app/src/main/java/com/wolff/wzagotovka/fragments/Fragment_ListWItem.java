package com.wolff.wzagotovka.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wolff.wzagotovka.R;
import com.wolff.wzagotovka.activities.Activity_WItem_Pager;
import com.wolff.wzagotovka.objects.WItem;
import com.wolff.wzagotovka.objects.WItemLab;

import java.util.List;
import java.util.UUID;


/**
 * Created by wolff on 03.04.2017.
 */

public class Fragment_ListWItem extends Fragment {
    private RecyclerView rvWItemList;
    WItemAdapter mItemAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_witem,container,false);
        rvWItemList = (RecyclerView)view.findViewById(R.id.rvWItemList);
        rvWItemList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvWItemList.setHasFixedSize(true);
        rvWItemList.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
       return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void updateUI(){
        WItemLab itemLab = WItemLab.get(getActivity());
        List<WItem> items = itemLab.getWItems();
        if(mItemAdapter==null) {
            mItemAdapter = new WItemAdapter(items);
        }
            //    rvWItemList.setAdapter(mItemAdapter);
        //} else {
            mItemAdapter.setWItems(items);
            rvWItemList.setAdapter(mItemAdapter);
            // mItemAdapter.notifyDataSetChanged();
        //}
        Log.e("UpdateUI","--------------------------------------------------------------------------");

    }

//==================================================================================================
public class WItemAdapter extends RecyclerView.Adapter<WItemHolder>{
    private List<WItem> mWItemList;
    public WItemAdapter(List<WItem> items){
        mWItemList = items;
    }

    @Override
    public WItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //View view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        View view = layoutInflater.inflate(R.layout.list_item_witem,parent,false);
        return new WItemHolder(view);
    }

    @Override
    public void onBindViewHolder(WItemHolder holder, int position) {
        WItem item = mWItemList.get(position);
        holder.bindWItem(item);
    }

    @Override
    public int getItemCount() {
        return mWItemList.size();
    }

    public void setWItems(List<WItem> items){
        mWItemList = items;
    }


}
    //==================================================================================================
public  class WItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private WItem mWItem;
    private TextView mTitleItem;
    //private TextView mMinTempItem;
    //private TextView mMaxTempItem;

    public WItemHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ///-mNameItem = (TextView) itemView;
        mTitleItem = (TextView) itemView.findViewById(R.id.tvTitle_listItem);
       // mIdItem = (TextView) itemView.findViewById(R.id.tvId_listItem);
    }
    public void bindWItem(WItem item){
        mWItem = item;
        //mIdItem.setText(mWItem.getId().toString());
        mTitleItem.setText(mWItem.getTitle()+"(сезон - "+mWItem.getSeason()+"), min: "+mWItem.getMinTemp()+" - max: "+mWItem.getMaxTemp());
    }

    @Override
    public void onClick(View v) {
        Intent intent = Activity_WItem_Pager.newIntent(v.getContext(),mWItem.getId());
        startActivity(intent);
        Log.e("CLICK ITEM",""+mWItem.getTitle()+" = "+mWItem.getId());
    }
}//==================================================================================================
}
