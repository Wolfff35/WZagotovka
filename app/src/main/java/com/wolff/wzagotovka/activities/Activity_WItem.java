package com.wolff.wzagotovka.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.wolff.wzagotovka.R;
import com.wolff.wzagotovka.fragments.Fragment_ListWItem;
import com.wolff.wzagotovka.objects.WItem;
import com.wolff.wzagotovka.objects.WItemLab;

import java.util.UUID;
import java.util.WeakHashMap;

public class Activity_WItem extends AppCompatActivity {
    EditText edName;
    public static final String EXTRA_WITEM_ID = "com.wolff.wzagotovka.witem_id";
    WItem mWItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witem);
        UUID wItemId = (UUID) getIntent().getSerializableExtra(EXTRA_WITEM_ID);
        mWItem = WItemLab.get(getApplicationContext()).getWItem(wItemId);

        edName = (EditText)findViewById(R.id.edName);
        edName.setText(mWItem.getName());
    }
    public static Intent newIntent(Context packageContext, UUID wItemId) {
        Intent intent = new Intent(packageContext, Activity_WItem.class);
        intent.putExtra(EXTRA_WITEM_ID, wItemId);
        return intent;
    }
}
