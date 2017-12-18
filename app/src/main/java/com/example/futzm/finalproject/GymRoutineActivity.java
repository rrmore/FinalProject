package com.example.futzm.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by futzm on 11/
 */
import java.util.HashMap;

public class GymRoutineActivity extends AppCompatActivity implements ProgramFragment.OnClickProgramsRviewListener,WeeksFragment.OnButtonClickListener,ExercisesFragment.OnClickExerciseRviewListener,ExerciseDetailsFragment.AddButtonClickListener,EnrollFragment.OnEnrollClickListener{
    Fragment mcontent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_routines_activity);
        Log.d("onCreate", "Inside task2 set content view");
        setTitle("");
        if (savedInstanceState == null)
        {
            mcontent = ProgramFragment.newInstance(R.id.recycler_view_fragment);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.program_activity_container,
                mcontent).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.pie_chart_item){
            Intent myIntent = new Intent(this, DashBoardActivity.class);
            startActivity(myIntent);
            return true;
        }
        /*
        if(id==R.id.add_program_item){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.program_activity_container, ProgramFragment.newInstance(1));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    public void onRecycleViewItemClicked (View v , HashMap<String,?> programs)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.program_activity_container, WeeksFragment.newInstance((Integer)programs.get("programId")));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onButtonClick(View view, int programId, String day) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.program_activity_container, ExercisesFragment.newInstance(programId,day));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
//@Override
    public void onExerciseItemClicked(View v, Exercise exercise) {
        ExerciseDetailsFragment details=ExerciseDetailsFragment.newInstance(exercise);
        details.setSharedElementEnterTransition(new DetailsTransition());
        details.setEnterTransition(new android.transition.Fade());
        details.setExitTransition(new android.transition.Fade());
        details.setSharedElementReturnTransition(new DetailsTransition());
        ImageView poster=(ImageView)v.findViewById(R.id.item_poster);
        poster.setTransitionName((String)exercise.getName());
        getSupportFragmentManager().beginTransaction()
                .addSharedElement(poster, poster.getTransitionName())
                .replace(R.id.program_activity_container,details)
                .addToBackStack(null)
                .commit();
        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.program_activity_container, );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        */

    }

    @Override
    public void onMenuClicked(int pId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.program_activity_container, EnrollFragment.newInstance(pId));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //@Override
    public void onClicked(View v, int position) {

    }

    @Override
    public void onAddButtonClickListener(int pid) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.program_activity_container, EnrollFragment.newInstance(pid));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enrollButtonClick() {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
    }
}
