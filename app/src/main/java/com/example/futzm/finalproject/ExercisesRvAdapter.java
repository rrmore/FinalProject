package com.example.futzm.finalproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by futzm on 9/29/2017.
 */
public class ExercisesRvAdapter extends RecyclerView.Adapter<ExercisesRvAdapter.ViewHolder>{
    private List<Exercise> mDataset=new ArrayList<>();
    private Context mContext;
    OnExerciseItemClickListener mExerciseItemClickListener;
    ImageView vPoster;
    TextView vTitle;

    public ExercisesRvAdapter(Context myContext,List<Exercise> myDataset) {
        mDataset = myDataset;
        mContext = myContext;
    }

    public interface OnExerciseItemClickListener {
        public void onExerciseItemClick(View view, int position);
    }

    public void setOnExerciseItemClickListener(final OnExerciseItemClickListener mExerciseItemClickListener) {
        this.mExerciseItemClickListener = mExerciseItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise=mDataset.get(position);
        holder.bindProgramsData(exercise);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View v){
            super(v);
            vPoster=(ImageView)v.findViewById(R.id.item_poster);
            vTitle=(TextView)v.findViewById(R.id.item_title);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mExerciseItemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            mExerciseItemClickListener.onExerciseItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });

        }
        public void bindProgramsData(final Exercise exercise){
            String posterpath=(String)exercise.getImage1();
            String poster=posterpath;
            Picasso.with(mContext)
                    .load(poster)
                    .into(vPoster);
            vTitle.setText((String)exercise.getName());
        }
    }
}
