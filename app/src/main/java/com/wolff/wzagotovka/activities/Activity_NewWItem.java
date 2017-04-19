package com.wolff.wzagotovka.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wolff.wzagotovka.R;
import com.wolff.wzagotovka.fragments.Fragment_WItem;
import com.wolff.wzagotovka.objects.WItem;

import java.util.UUID;

import static com.wolff.wzagotovka.activities.Activity_WItem_Pager.EXTRA_WITEM_ID;

public class Activity_NewWItem extends AppCompatActivity {
private WItem newWItem;
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Activity_NewWItem.class);
        //intent.putExtra(EXTRA_WITEM_ID, witemId);
        //Log.e("ACTIVITY NEW INTENT"," "+witemId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_witem);
        //UUID wItemId = (UUID) getIntent().getSerializableExtra(EXTRA_WITEM_ID);
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //newWItem = new WItem();
        Fragment fragment = Fragment_WItem.newInstance(null,getApplicationContext());
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_new_witem, fragment);
        fragmentTransaction.commit();

    }
}
