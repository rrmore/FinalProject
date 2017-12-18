package com.example.futzm.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by futzm on 11/22/2017.
 */

public class ProfileContentFragment extends Fragment {

    TextView firstName;
    TextView lastName;
    TextView age;
    TextView sex;
    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;

    static ProfileContentFragment newInstance(){
        ProfileContentFragment pcf=new ProfileContentFragment();
        return pcf;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_details, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firstName=(TextView)rootView.findViewById(R.id.first_name_text);
        lastName=(TextView)rootView.findViewById(R.id.last_name_text);
        sex=(TextView)rootView.findViewById(R.id.sex_text);
        age=(TextView)rootView.findViewById(R.id.age_text);
        Button editButton=(Button)rootView.findViewById(R.id.edit_button);
        String email=firebaseAuth.getCurrentUser().getEmail();
        String userName=email.replace(".","_");
        myRef= FirebaseDatabase.getInstance().getReference("Users").child(userName);
        final DataSnapshot[] testing = new DataSnapshot[1];
        final Users[] user = {new Users()};
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user=dataSnapshot.getValue(Users.class);
                Log.d("Name= ",user.getFirstName());
                Log.d("Name= ",user.getLastName());
                firstName.setText("First Name: "+user.getFirstName());
                lastName.setText("Last Name: "+user.getLastName());
                sex.setText("Sex: "+user.getSex());
                age.setText("Age: "+user.getAge());
                /*
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children)
                {
                     String users= child.getValue(String.class);
                     sex.setText(users);
                    //users.add(user);
                }
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //firstName.setText("First Name: "+getValueInFB("firstName"));
        //lastName.setText("Last Name: "+getValueInFB("lastName"));
        //age.setText("Sex: "+getValueInFB("sex"));
        //sex.setText("Age: "+getValueInFB("age"));
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.framelayout_with_profiledetails,ProfileEditFragment.newInstance()).addToBackStack(null).commit();
            }
        });
        return rootView;


    }

}
