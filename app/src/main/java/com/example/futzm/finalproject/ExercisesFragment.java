package com.example.futzm.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Created by futzm on 11/25/2017.
 */

public class ExercisesFragment extends Fragment {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    ExercisesRvAdapter mExercisesRvAdapter;
    static String day;
    static int programId;
    List<Exercise> exerciseList;
    private OnClickExerciseRviewListener mOnClickExerciseRviewListener;
    ProgramsData programsData;

    public interface OnClickExerciseRviewListener {
        public void onExerciseItemClicked(View v, Exercise exercise);
        public void onMenuClicked(int pId);
    }

    public static ExercisesFragment newInstance(int pId,String dy) {
        day=dy;
        programId=pId;
        ExercisesFragment mr = new ExercisesFragment();
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
        final View rootView = inflater.inflate(R.layout.exercise_display_fragment, container, false);
        setHasOptionsMenu(true);
        //final ProgramsData programsData= new ProgramsData();
        exerciseList=new ArrayList<>();
        mOnClickExerciseRviewListener=(OnClickExerciseRviewListener) rootView.getContext();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.exercises_card_list);
        DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Exercises").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children)
                {
                    Exercise ex=child.getValue(Exercise.class);
                     if(ex.getDay().equals(day)&ex.getProgramId()==programId)
                     {
                         exerciseList.add(ex);
                     }
                }
                mExercisesRvAdapter = new ExercisesRvAdapter(getContext(), exerciseList);
                mRecyclerView.setAdapter(mExercisesRvAdapter);
                mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mExercisesRvAdapter.setOnExerciseItemClickListener(new ExercisesRvAdapter.OnExerciseItemClickListener() {
                    @Override
                    public void onExerciseItemClick(View view, int position) {
                        mOnClickExerciseRviewListener.onExerciseItemClicked(view,exerciseList.get(position));
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        itemAnimation();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_program_item){
            mOnClickExerciseRviewListener.onMenuClicked(programId);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void itemAnimation() {
        SlideInLeftAnimator iAnimation = new SlideInLeftAnimator() ;
        iAnimation.setAddDuration(1000);
        iAnimation.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(iAnimation);
    }

}