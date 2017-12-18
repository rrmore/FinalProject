package com.example.futzm.finalproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.futzm.finalproject.GymRoutineRvAdapter;
import com.example.futzm.finalproject.ProgramsData;

import java.util.ArrayList;
import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Created by futzm on 9/24/2017.
 */

public class ProgramFragment extends Fragment {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    GymRoutineRvAdapter mGymRoutineRvAdapter;
    private OnClickProgramsRviewListener mOnClickProgramsRviewListener;
    ProgramsData programsData;

    public interface OnClickProgramsRviewListener {
        public void onRecycleViewItemClicked(View v, HashMap<String, ?> program);
    }

    public static ProgramFragment newInstance(int section_number) {
        ProgramFragment mr = new ProgramFragment();
        Bundle args = new Bundle();
        return mr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.programs_display_fragment, container, false);
        final ProgramsData programsData= new ProgramsData();
        mOnClickProgramsRviewListener=(OnClickProgramsRviewListener) rootView.getContext();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.programs_card_list);
        mGymRoutineRvAdapter = new GymRoutineRvAdapter(getContext(), ProgramsData.programsList);
        mRecyclerView.setAdapter(mGymRoutineRvAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mGymRoutineRvAdapter.setOnProgramItemClickListener(new GymRoutineRvAdapter.OnProgramItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HashMap<String, ?> program = (HashMap<String, ?>) programsData.getItem(position);
                mOnClickProgramsRviewListener.onRecycleViewItemClicked(view, program);
            }
        });
        itemAnimation();
        return rootView;
    }

    private void itemAnimation() {
        SlideInLeftAnimator iAnimation = new SlideInLeftAnimator() ;
        iAnimation.setAddDuration(1000);
        iAnimation.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(iAnimation);
    }

}

