package com.example.futzm.finalproject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by futzm on 12/6/2017.
 */

public class ExerciseTrackerActivity extends AppCompatActivity implements WeeksFragment.OnButtonClickListener,ExerciseTrackerFragment.OnSaveButtonClickListener{
    Fragment mcontent;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_tracker_activity);
        String userName=FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","_");
        databaseReference=FirebaseDatabase.getInstance().getReference().child("ExerciseTracker").child(userName);
        Log.d("onCreate", "Inside task2 set content view");
        setTitle("");
        getSupportFragmentManager().beginTransaction().replace(R.id.exercise_tracker_activity_container,WeeksFragment.newInstance(0)).commit();

    }

    @Override
    public void onButtonClick(View view, int programId, String day) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        overridePendingTransition(R.anim.slide_right,R.anim.slide_left);
        fragmentTransaction.replace(R.id.exercise_tracker_activity_container, ExerciseTrackerFragment.newInstance(0,day));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveButtonClick(List<ExerciseTracker> et) {
        boolean isCompleted=true;
        for (ExerciseTracker exerciseTracker:et) {
            if(exerciseTracker.getStatus().equalsIgnoreCase("no")){
                isCompleted=false;

            }
            databaseReference.child(exerciseTracker.getExercise()).child("status").setValue(exerciseTracker.getStatus());
            //}
        }
        if(isCompleted==true){
            Intent intent=new Intent(this,CongratulateActivity.class);
            startActivity(intent);
        }
        else {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();
        }
    }

}
