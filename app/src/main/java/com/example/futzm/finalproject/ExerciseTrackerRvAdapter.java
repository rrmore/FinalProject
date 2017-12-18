package com.example.futzm.finalproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by futzm on 12/7/2017.
 */

public class ExerciseTrackerRvAdapter extends RecyclerView.Adapter<ExerciseTrackerRvAdapter.ViewHolder>{
    private static List<ExerciseTracker> mDataset;
    private Context mContext;
    OnExerciseListClickListener mExerciseListClickListener;
    CheckBox vCheckBox;
    TextView vName;
    @Override
    public ExerciseTrackerRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_tracker_item, parent, false);
        ExerciseTrackerRvAdapter.ViewHolder viewHolder = new ExerciseTrackerRvAdapter.ViewHolder(view);
        return viewHolder;
    }

    public  List<ExerciseTracker> getList(){
        return mDataset;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExerciseTracker exerciseTracker=mDataset.get(position);
        holder.bindProgramsData(exerciseTracker);
    }
    public ExerciseTrackerRvAdapter(Context myContext,List<ExerciseTracker> myDataset) {
        mDataset = myDataset;
        mContext = myContext;
        }

    public interface OnExerciseListClickListener {
    public void onListClick(View view, int position);
    }

    public void setOnProgramItemClickListener(final ExerciseTrackerRvAdapter.OnExerciseListClickListener mExerciseListClickListener) {
        this.mExerciseListClickListener = mExerciseListClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View v){
        super(v);
        vCheckBox =(CheckBox) v.findViewById(R.id.checkBox_item);
        vName=(TextView)v.findViewById(R.id.exercise_name_item);

    }

    public void bindProgramsData(final ExerciseTracker exerciseTracker){
        vName.setText(exerciseTracker.getExercise());
        if(exerciseTracker.getStatus().equalsIgnoreCase("yes")){
            vCheckBox.setChecked(true);
        }
        vCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    exerciseTracker.setStatus("yes");
                }
                else{
                    exerciseTracker.setStatus("no");
                }
            }
        });
    }
}
}
