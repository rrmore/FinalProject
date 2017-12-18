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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Created by futzm on 12/6/2017.
 */

public class ExerciseTrackerFragment extends Fragment {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    ExerciseTrackerRvAdapter mTrackerRvAdapter;
    private ProgramFragment.OnClickProgramsRviewListener mOnClickProgramsRviewListener;
    static List<ExerciseTracker> exerciseTrackerFullList;
    static List<ExerciseTracker> exerciseTrackerList;
    static String day;
    DatabaseReference databaseReference;
    Button saveRecords;
    OnSaveButtonClickListener mSaveButtonClickListener;

    public interface OnClickExerciseRviewListener {
        public void onRecycleViewItemClicked(View v, HashMap<String, ?> program);
    }

    public interface OnSaveButtonClickListener{
        public void onSaveButtonClick(List<ExerciseTracker> et);
    }

    public static ExerciseTrackerFragment newInstance(int pid,String dy) {
        ExerciseTrackerFragment et = new ExerciseTrackerFragment();
        Bundle args = new Bundle();
        day=dy;
        return et;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.exercise_tracker_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.exercises_tracker_card_list);
        saveRecords=(Button)rootView.findViewById(R.id.save_records);
        String username= FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","_");
        mSaveButtonClickListener=(OnSaveButtonClickListener)getContext();
        databaseReference= FirebaseDatabase.getInstance().getReference("ExerciseTracker").child(username);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                exerciseTrackerFullList=new ArrayList<>();
                exerciseTrackerList=new ArrayList<>();
                for(DataSnapshot child:children)
                {

                    ExerciseTracker ex=child.getValue(ExerciseTracker.class);
                    if(ex.getDay().equals(day))
                    {
                        exerciseTrackerList.add(ex);
                    }
                    else{
                        exerciseTrackerFullList.add(ex);
                    }
                }
                mTrackerRvAdapter = new ExerciseTrackerRvAdapter(getContext(), exerciseTrackerList);
                mRecyclerView.setAdapter(mTrackerRvAdapter);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mTrackerRvAdapter.setOnProgramItemClickListener(new ExerciseTrackerRvAdapter.OnExerciseListClickListener() {
                    @Override
                    public void onListClick(View view, int position) {

                    }
                });
                itemAnimation();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        saveRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ExerciseTracker> updatedList=mTrackerRvAdapter.getList();
                for(ExerciseTracker ex:updatedList){
                    exerciseTrackerFullList.add(ex);
                }
                mSaveButtonClickListener.onSaveButtonClick(exerciseTrackerFullList);
            }
        });
        return rootView;
    }

    private void itemAnimation() {
        SlideInLeftAnimator iAnimation = new SlideInLeftAnimator() ;
        iAnimation.setAddDuration(1000);
        iAnimation.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(iAnimation);
    }
}
