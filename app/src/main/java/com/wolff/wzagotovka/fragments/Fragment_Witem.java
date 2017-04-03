package com.wolff.wzagotovka.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wolff.wzagotovka.R;

/**
 * Created by wolff on 03.04.2017.
 */

public class Fragment_Witem extends Fragment {
    private EditText mEdName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //eturn super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_witem,container,false);
        mEdName = (EditText)view.findViewById(R.id.edName);
        mEdName.setOnClickListener(edTextOnClickListener);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//==================================================================================================
    private View.OnClickListener edTextOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
};
}
