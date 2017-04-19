package com.wolff.wzagotovka.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.wolff.wzagotovka.R;
import com.wolff.wzagotovka.activities.Activity_WItem_Pager;
import com.wolff.wzagotovka.objects.WItem;
import com.wolff.wzagotovka.objects.WItemLab;

import java.io.File;
import java.util.UUID;

/**
 * Created by wolff on 05.04.2017.
 */

public class Fragment_WItem extends Fragment {
    private WItem mWItem;
    private EditText edTitle;
    private Spinner spSeason;
    private SeekBar seekMinT;
    private SeekBar seekMaxT;
    private Button btnAddDate;
    private ImageView imPhoto;
    private static final String ARG_WITEM_ID = "WItem_ID";
    private static final String ARG_ISNEWITEM_ID = "isNewItem";
    private static final int REQUEST_PHOTO= 2;
    private int seekDelta = 5;
    private  boolean isNewItem;
    private File mPhotoFile;
    @Override
    public void onPause() {
        super.onPause();
        ////WItemLab.get(getActivity()).updateWItem(mWItem);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
 //       String arg = (String) getArguments().getSerializable(ARG_WITEM_ID);
        UUID itemId = (UUID) getArguments().getSerializable(ARG_WITEM_ID);

        Log.e("ARG_WITEM_ID"," = "+itemId);
        if(itemId!=null) {
            isNewItem=false;
            mWItem = WItemLab.get(getActivity()).getWItem(itemId);
        }else {
            isNewItem=true;
            mWItem = new WItem();
        }
        mPhotoFile = WItemLab.get(getActivity()).getPhotoFile(mWItem);




        Log.e("FRAGMENT ON CREATE","Fragment_WItem "+mWItem.getTitle()+" = "+itemId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.activity_witem,container,false);
        edTitle = (EditText) v.findViewById(R.id.edTitle);
        spSeason = (Spinner) v.findViewById(R.id.spSeason);
        seekMinT = (SeekBar) v.findViewById(R.id.seekMinT);
        seekMaxT = (SeekBar) v.findViewById(R.id.seekMaxT);
        btnAddDate = (Button) v.findViewById(R.id.btnAddDate);
        imPhoto = (ImageView) v.findViewById(R.id.imPhoto);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getActivity().getPackageManager();
        boolean canTakePhoto = mPhotoFile!=null&&captureImage.resolveActivity(packageManager)!=null;
        imPhoto.setEnabled(canTakePhoto);
        if(canTakePhoto){
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);

        }
        edTitle.setText(mWItem.getTitle());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.WSeasons));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSeason.setAdapter(spinnerArrayAdapter);
        spSeason.setSelection(spinnerArrayAdapter.getPosition(mWItem.getSeason()));
        seekMinT.setProgress(mWItem.getMinTemp()-seekDelta);
        seekMaxT.setProgress(mWItem.getMaxTemp()-seekDelta);

        //btnAddDate.setText(mWItem.getAddDate().toString());
        btnAddDate.setEnabled(false);

        edTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWItem.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        spSeason.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[]choose = getResources().getStringArray(R.array.WSeasons);
                mWItem.setSeason(choose[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seekMinT.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        seekMaxT.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        imPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage,REQUEST_PHOTO);
            }
        });
        return v;
    }

    SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()){
                case R.id.seekMinT:
                    mWItem.setMinTemp(progress);
                    if(seekMinT.getProgress()>seekMaxT.getProgress()){
                        seekMaxT.setProgress(seekMinT.getProgress());
                    }
                   // Log.e("SEEK","MIN = "+progress+";   "+seekBar.getTag());

                    break;
                case R.id.seekMaxT:
                    mWItem.setMaxTemp(progress-seekDelta);
                    if(seekMinT.getProgress()>seekMaxT.getProgress()){
                        seekMinT.setProgress(seekMaxT.getProgress());
                    }
                   // Log.e("SEEK","MAX = "+progress+";  "+";   "+seekBar.getTag());
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
     //   if(mWItem.getTitle()!=null){
     //       if(!mWItem.getTitle().isEmpty()){
     //           if(isNewItem) {
     //               WItemLab.get(getContext()).addWItem(mWItem);
     //           }else {
     //               WItemLab.get(getContext()).updateWItem(mWItem);
     //           }
     //       }
     //   }
        //WItemLab.get(getApplicationContext()).addWItem(item);
     //   Log.e("FRAGMENT WITEM","DESTROY!!!");
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_witem, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_witem_save:
                mWItem.setTitle(edTitle.getText().toString());
                mWItem.setMinTemp(seekMinT.getProgress()-seekDelta);
                mWItem.setMaxTemp(seekMaxT.getProgress()-seekDelta);
                //mWItem.setSeason(spSeason.getSelectedItem());
                String[]choose = getResources().getStringArray(R.array.WSeasons);
                mWItem.setSeason(choose[spSeason.getSelectedItemPosition()]);
                Log.e("SPINNER"," = "+mWItem.getSeason());
                if(mWItem.getTitle()!=null){
                       if(!mWItem.getTitle().isEmpty()){
                           if(isNewItem) {
                               WItemLab.get(getContext()).addWItem(mWItem);
                           }else {
                               WItemLab.get(getContext()).updateWItem(mWItem);

                           }
                       }

                   }
                getActivity().finish();
                 return true;
            case R.id.menu_witem_delete:
                WItemLab.get(getContext()).deleteWItem(mWItem);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Fragment_WItem newInstance(UUID wItemId, Context context) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_WITEM_ID,wItemId);
        //args.putBoolean(ARG_ISNEWITEM_ID,isNewItem);

        Fragment_WItem fragment = new Fragment_WItem();
        fragment.setArguments(args);
        //Log.e("FRAGMENT NEW INSTANCE ",""+WItemLab.get(context).getWItem(wItemId).getTitle()+"    "+wItemId);
        return fragment;
    }
}
