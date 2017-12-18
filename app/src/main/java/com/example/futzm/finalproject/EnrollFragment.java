package com.example.futzm.finalproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.Inflater;

/**
 * Created by futzm on 11/28/2017.
 */

public class EnrollFragment extends Fragment {
    Button enrollButton;
    TimePicker timePicker;
    static int programId;
    View rootview;
    final List<Exercise> exerciseList=new ArrayList<>();
    AlarmManager alarmManager;
    OnEnrollClickListener onEnrollClickListener;
    static EnrollFragment newInstance(int pid){
        programId=pid;
        EnrollFragment enrollFragment=new EnrollFragment();
        return enrollFragment;
    }

    interface OnEnrollClickListener{
        public void enrollButtonClick();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview= inflater.inflate(R.layout.enroll_fragment,container,false);
        onEnrollClickListener=(OnEnrollClickListener)getContext();
        enrollButton=(Button)rootview.findViewById(R.id.enroll);
        timePicker=(TimePicker)rootview.findViewById(R.id.timePicker);
        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("enroll button clicked","");
                forAllDay();
                addInTrackerTable(programId);
                addInUserTable(programId);
                Toast.makeText(getContext(), "Reminder is set from monday to Friday", Toast.LENGTH_LONG).show();
                onEnrollClickListener.enrollButtonClick();
                Log.d("ABC","ABc");
            }
        });
        return rootview;
    }

    public void forAllDay(){
        AlarmManager alarmManager2=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager3=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager4=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager5=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager1=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calSet=Calendar.getInstance();
        calSet.set(Calendar.DAY_OF_WEEK, 2);
        calSet.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calSet.set(Calendar.MINUTE, timePicker.getMinute());
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        Intent intent=new Intent(getActivity(),AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity().getApplicationContext(),0,intent,0);
        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        /*
        calSet.set(Calendar.DAY_OF_WEEK, 3);
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        calSet.set(Calendar.DAY_OF_WEEK, 4);
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        calSet.set(Calendar.DAY_OF_WEEK, 5);
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        calSet.set(Calendar.DAY_OF_WEEK, 6);
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        */
    }

    public void addInTrackerTable(int pid){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String email=firebaseAuth.getCurrentUser().getEmail();
        final String userName=firebaseAuth.getCurrentUser().getEmail().replace(".","_");
        DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Exercises").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children)
                {
                    Exercise ex=child.getValue(Exercise.class);
                    if(ex.getProgramId()==programId)
                    {
                        exerciseList.add(ex);
                    }
                }
                DatabaseReference dbRef =FirebaseDatabase.getInstance().getReference().child("ExerciseTracker").child(userName);
                for (Exercise ex:exerciseList) {
                    ExerciseTracker exerciseTracker=new ExerciseTracker(ex.getName(),ex.getDay());
                    dbRef.child(ex.getName()).setValue(exerciseTracker);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void forday(int week) {
        alarmManager=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calSet=Calendar.getInstance();
        calSet.set(Calendar.DAY_OF_WEEK, week);
        calSet.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calSet.set(Calendar.MINUTE, timePicker.getMinute());
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        Intent intent=new Intent(getActivity(),AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity().getApplicationContext(),0,intent,0);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000*5,pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void addInUserTable(int pid){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String email=firebaseAuth.getCurrentUser().getEmail();
        final String userName=firebaseAuth.getCurrentUser().getEmail().replace(".","_");
        DatabaseReference databaseReference;
        FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("programId").setValue(pid);

    }
}
