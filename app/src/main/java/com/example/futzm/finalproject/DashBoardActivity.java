package com.example.futzm.finalproject;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;

/**
 * Created by futzm on 12/7/2017.
 */

public class DashBoardActivity extends AppCompatActivity {
    PieChart pieChart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        overridePendingTransition(R.anim.slide_right,R.anim.slide_left);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dashboard_container, PieChartFragment.newInstance());
        fragmentTransaction.commit();
    }
}
