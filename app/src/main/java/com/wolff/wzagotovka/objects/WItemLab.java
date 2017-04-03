package com.wolff.wzagotovka.objects;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wolff on 03.04.2017.
 */

public class WItemLab {
    private static WItemLab sWItemLab;

    private List<WItem> mWItemList;

    private WItemLab(Context context){

        mWItemList = new ArrayList<>();
        for(int i=0;i<100;i++){
            WItem item = new WItem();
            item.setName("Item # "+i);
            mWItemList.add(item);
        }
    }

    public static WItemLab get(Context context){
        if(sWItemLab==null){
            sWItemLab = new WItemLab(context);
        }
        return sWItemLab;
    }
    public List<WItem> getWItems(){
        return mWItemList;
    }
    public WItem getWItem(UUID id){
        for(WItem item:mWItemList){
            if(item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }
}
