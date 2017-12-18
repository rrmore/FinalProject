package com.example.futzm.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by futzm on 11/22/2017.
 */

public class ProfileEditFragment extends Fragment {

    EditText firstName;
    EditText lastName;
    EditText sex;
    EditText age;
    FirebaseAuth firebaseAuth;
    DatabaseReference ref;
    static ProfileEditFragment newInstance(){
        ProfileEditFragment pef=new ProfileEditFragment();
        return pef;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        String userName=firebaseAuth.getCurrentUser().getEmail().replace(".","_");
        ref= FirebaseDatabase.getInstance().getReference("Users").child(userName);
        firstName=(EditText) rootView.findViewById(R.id.first_name);
        lastName=(EditText)rootView.findViewById(R.id.last_name_txt);
        sex=(EditText)rootView.findViewById(R.id.sex_text);
        age=(EditText)rootView.findViewById(R.id.age_text);
        Button saveButton=(Button)rootView.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("firstName").setValue(firstName.getText().toString());
                ref.child("lastName").setValue(lastName.getText().toString());
                ref.child("sex").setValue(sex.getText().toString());
                ref.child("age").setValue(age.getText().toString());
                getFragmentManager().beginTransaction().replace(R.id.framelayout_with_profiledetails,ProfileContentFragment.newInstance()).addToBackStack(null).commitAllowingStateLoss();
            }
        });
        return rootView;
    }
}
