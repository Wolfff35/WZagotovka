package com.wolff.wzagotovka.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wzagotovka.R;

/**
 * Created by wolff on 18.04.2017.
 */

public abstract class Activity_SingleFragment extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witem_pager);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.viewPager_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.viewPager_container, fragment).commit();
        }
    }
}
