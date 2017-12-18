package com.example.futzm.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by futzm on 11/27/2017.
 */

public class ExerciseDetailsFragment extends Fragment {
    static Exercise exercise;
    static List<String> imageUrls;
    ViewPager vp;
    AddButtonClickListener mAddButtonClickListener;
    ViewPager poster;

    interface AddButtonClickListener{
        public void onAddButtonClickListener(int pid);
    }

    static ExerciseDetailsFragment newInstance(Exercise ex){
         exercise=ex;
        imageUrls=new ArrayList<>();
        imageUrls.add(ex.getImage1());
        imageUrls.add(ex.getImage2());
        ExerciseDetailsFragment exerciseFragment=new ExerciseDetailsFragment();
        return exerciseFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_details_fragment, container, false);
        poster=(ViewPager) rootView.findViewById(R.id.exercise_photo_pager);
        poster.setTransitionName(exercise.getName());
        mAddButtonClickListener=(AddButtonClickListener)getContext();
        TextView title=(TextView)rootView.findViewById(R.id.exercise_title);
        TextView description=(TextView)rootView.findViewById(R.id.description);
        TextView reps=(TextView)rootView.findViewById(R.id.reps);
        TextView sets=(TextView)rootView.findViewById(R.id.sets);
        Button enroll=(Button)rootView.findViewById(R.id.addProgram);
        title.setText(exercise.getName());
        description.setText("Description: "+exercise.getDescription());
        reps.setText("Reps: "+String.valueOf(exercise.getReps()));
        sets.setText("Sets: "+String.valueOf(exercise.getSets()));
        MyFragmentPagerAdapter mf=new MyFragmentPagerAdapter(getChildFragmentManager(),imageUrls.size());
        vp = (ViewPager) rootView.findViewById(R.id.exercise_photo_pager);
        vp.setAdapter(mf);
        vp.setCurrentItem(0);
        Timer timer=new Timer();
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddButtonClickListener.onAddButtonClickListener(exercise.getProgramId());
            }
        });
       // timer.scheduleAtFixedRate(new MytimerTask(),2000,2000);
        return rootView;
    }

    /*
    public  class MytimerTask extends TimerTask {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (vp.getCurrentItem() == 0) {
                        vp.setCurrentItem(1);
                    } else {
                        vp.setCurrentItem(0);
                    }
                }
            });
        }
    }
    */

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        int pageSize;

        public MyFragmentPagerAdapter(FragmentManager fm, int size) {
            super(fm);
            pageSize = size;
        }


        @Override
        public int getCount() {
            return pageSize;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            //HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
            //String name = (String) movie.get("name");
            return "";
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return VpagerPhotoFragment.newInstance(imageUrls.get(position));
        }


    }

}


