package com.example.futzm.finalproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by futzm on 9/29/2017.
 */
public class GymRoutineRvAdapter extends RecyclerView.Adapter<GymRoutineRvAdapter.ViewHolder>{
    private List<Map<String,?>> mDataset;
    private Context mContext;
    OnProgramItemClickListener mProgramItemClickListener;
    ImageView vPoster;
    TextView vOverview;

    public GymRoutineRvAdapter(Context myContext,List<Map<String, ?>> myDataset) {
        mDataset = myDataset;
        mContext = myContext;
    }

    public interface OnProgramItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnProgramItemClickListener(final OnProgramItemClickListener mProgramItemClickListener) {
        this.mProgramItemClickListener = mProgramItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String,?> program=mDataset.get(position);
        holder.bindProgramsData(program);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_item, parent, false);
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
            vOverview=(TextView)v.findViewById(R.id.item_overview);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mProgramItemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            mProgramItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });

        }
        public void bindProgramsData(final Map<String,?> program){
            vPoster.setImageResource((Integer) program.get("image"));
            vOverview.setText((String)program.get("programName"));
        }
    }
}
