package com.wolff.wzagotovka.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wolff.wzagotovka.R;
import com.wolff.wzagotovka.fragments.Fragment_WItem;
import com.wolff.wzagotovka.objects.WItem;
import com.wolff.wzagotovka.objects.WItemLab;

import java.util.List;
import java.util.UUID;

public class Activity_WItem_Pager extends FragmentActivity {
    private ViewPager mViewPager;
    private List<WItem> mWItems;
    public static final String EXTRA_WITEM_ID = "witem_id";
    public static final String EXTRA_ISNEWITEM = "isNewItem";

    public static Intent newIntent(Context packageContext, UUID witemId,boolean isNewItem) {
        Intent intent = new Intent(packageContext, Activity_WItem_Pager.class);
        intent.putExtra(EXTRA_WITEM_ID, witemId);
        intent.putExtra(EXTRA_ISNEWITEM, isNewItem);
        Log.e("ACTIVITY NEW INTENT"," "+witemId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witem_pager);
        UUID wItemId = (UUID) getIntent().getSerializableExtra(EXTRA_WITEM_ID);
        final boolean isNewItem = getIntent().getBooleanExtra(EXTRA_ISNEWITEM,false);
        Log.e("ACTIVITY ON CREATE ",""+wItemId);
        mViewPager = (ViewPager)findViewById(R.id.viewPager_container);
        mWItems = WItemLab.get(this).getWItems();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                //
                WItem item = mWItems.get(position);
                //Log.e("GET ITEM = ",""+position+"; current item = "+mViewPager.getCurrentItem());
                return Fragment_WItem.newInstance(item.getId(),getApplicationContext(),isNewItem);
            }

            @Override
            public int getCount() {
                return mWItems.size();
            }
        });
        for (int i = 0; i < mWItems.size(); i++) {
            if (mWItems.get(i).getId().equals(wItemId)) {
                mViewPager.setCurrentItem(i);
                Log.e("SHOWWWW",""+mViewPager.getCurrentItem()+"; i="+i);
                break;
            }
       }

    }
}
