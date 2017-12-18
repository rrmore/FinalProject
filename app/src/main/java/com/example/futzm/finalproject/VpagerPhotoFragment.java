package com.example.futzm.finalproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by futzm on 11/27/2017.
 */

public class VpagerPhotoFragment extends Fragment {

    static String photoUrl;
    static VpagerPhotoFragment newInstance(String url){
        photoUrl=url;
        VpagerPhotoFragment vpf=new VpagerPhotoFragment();
        return vpf;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_fragment, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.viewpager_photo);
        Picasso.with(getContext())
                .load(photoUrl)
                .into(imageView);
        return view;
    }
}
