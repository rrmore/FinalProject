package com.example.futzm.finalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by futzm on 12/7/2017.
 */

public class PieChartFragment extends Fragment {

    PieChart pieChart;

    static PieChartFragment newInstance(){
        PieChartFragment pieChartFragment=new PieChartFragment();
        return pieChartFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dashboard_layout, container, false);
        pieChart=(PieChart)rootView.findViewById(R.id.chart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleAlpha(0);
        getCompletedPercentage();
        return rootView;
    }

    public void getCompletedPercentage(){
        String userName= FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","_");
        DatabaseReference dRef= FirebaseDatabase.getInstance().getReference("ExerciseTracker").child(userName);
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> amountDone=new ArrayList<>();
                List<ExerciseTracker> exerciseTrackerList=new ArrayList<>();
                ArrayList<PieEntry> percentage=new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child:children)
                {
                    ExerciseTracker ex=child.getValue(ExerciseTracker.class);
                        exerciseTrackerList.add(ex);

                }
                float totalsize=exerciseTrackerList.size();
                float completedSize=0;
                for(ExerciseTracker exerciseTracker:exerciseTrackerList){
                    if(exerciseTracker.getStatus().equalsIgnoreCase("yes")){
                        completedSize++;
                    }
                }
                float completePercent=(completedSize/totalsize)*100;
                percentage.add(new PieEntry(completePercent,"Completed"));
                percentage.add(new PieEntry(100-completePercent,"Remaining"));
                amountDone.add("Done");
                amountDone.add("Remaining");
                PieDataSet pieDataSet=new PieDataSet(percentage,"");
                pieDataSet.setSliceSpace(2);

                //Set colors
                ArrayList<Integer> colors=new ArrayList<>();
                colors.add(Color.CYAN);
                colors.add(Color.RED);

                pieDataSet.setColors(colors);

                //Add Legend
                Legend legend=pieChart.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

                PieData pieData=new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.setEntryLabelColor(Color.BLACK);
                pieChart.setUsePercentValues(true);
                pieChart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
