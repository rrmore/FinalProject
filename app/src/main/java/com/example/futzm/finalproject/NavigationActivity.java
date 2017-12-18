package com.example.futzm.finalproject;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by futzm on 11/21/2017.
 */

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.project_orange)));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        //------
        NavigationView navigation_View = (NavigationView) findViewById(R.id.navigation_view);
        View hView =  navigation_View.getHeaderView(0);
        final ImageView profile = (ImageView) hView.findViewById(R.id.profile_pic);
        String userName= FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".","_");
        DatabaseReference dRef= FirebaseDatabase.getInstance().getReference("Users").child(userName).child("programId");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Integer value=dataSnapshot.getValue(Integer.class);
                    if(value==null||value==0){
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_drawer_layout, new EmptyFragment());
                        fragmentTransaction.commitAllowingStateLoss();
                    }
                    else {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_drawer_layout, PieChartFragment.newInstance());
                        fragmentTransaction.commitAllowingStateLoss();
                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference dRef1= FirebaseDatabase.getInstance().getReference("Users").child(userName).child("photoUrl");
        dRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                String value=dataSnapshot.getValue(String.class);
                if(value.equals("")||value==null){
                    profile.setImageResource(R.drawable.default_profile_pic);
                }
                else {
                    Picasso.with (NavigationActivity.this). load (value). into (profile);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerview) {
                super.onDrawerOpened(drawerview);
            }

            @Override
            public void onDrawerClosed(View drawerview) {
                super.onDrawerClosed(drawerview);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setEnterTransition(slide);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.dummy) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile_item) {
            intent = new Intent(NavigationActivity.this, ProfileActivity.class);
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left);
            startActivity(intent);
        }
        if(id==R.id.gym_plan_item){
            intent = new Intent(NavigationActivity.this, GymRoutineActivity.class);
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left);
            startActivity(intent);
        }
        if(id==R.id.routine_tracker_item){
            intent = new Intent(NavigationActivity.this, ExerciseTrackerActivity.class);
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left);
            startActivity(intent);
        }
        if(id==R.id.dashboard_item){
            intent = new Intent(NavigationActivity.this,DashBoardActivity.class);
            overridePendingTransition(R.anim.slide_right,R.anim.slide_left);
            startActivity(intent);
        }
        if(id==R.id.maps_item){
            Intent intent = new Intent(this,MapsActivity.class);
            startActivity(intent);
        }
        if(id==R.id.sign_out_item){
            finish();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
