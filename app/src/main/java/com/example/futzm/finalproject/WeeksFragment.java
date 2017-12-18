package com.example.futzm.finalproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by futzm on 11/25/2017.
 */

public class WeeksFragment extends Fragment {

    Button monday;
    Button tuesday;
    Button wednesday;
    Button thursday;
    Button friday;
    static int programId;
    OnButtonClickListener mOnButtonClickListener;

    public interface OnButtonClickListener {
        public void onButtonClick(View view, int programId, String day);
    }

    public static WeeksFragment newInstance(int pId){
        WeeksFragment weeksFragment=new WeeksFragment();
        programId=pId;
        return weeksFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weeks_display_fragment, container, false);
        mOnButtonClickListener=(OnButtonClickListener)rootView.getContext();
        monday=(Button)rootView.findViewById(R.id.monday);
        tuesday=(Button)rootView.findViewById(R.id.tuesday);
        wednesday=(Button)rootView.findViewById(R.id.wednesday);
        thursday=(Button)rootView.findViewById(R.id.thursday);
        friday=(Button)rootView.findViewById(R.id.friday);
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClick(v,programId,"Monday");
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClick(v,programId,"Tuesday");
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClick(v,programId,"Wednesday");
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClick(v,programId,"Thursday");
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onButtonClick(v,programId,"Friday");
            }
        });
        return rootView;
    }
}
