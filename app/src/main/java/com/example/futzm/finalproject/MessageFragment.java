package com.example.futzm.finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by futzm on 12/8/2017.
 */

public class MessageFragment extends Fragment {
    OnCloseClickListener onCloseClickListener;
    interface OnCloseClickListener{
        public void onCloseClick();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message_layout, container, false);
        onCloseClickListener=(OnCloseClickListener)getContext();
        Button close=(Button)rootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseClickListener.onCloseClick();
            }
        });
        return rootView;
    }
}
