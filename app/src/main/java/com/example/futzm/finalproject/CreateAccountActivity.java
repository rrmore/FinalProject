package com.example.futzm.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.firebase.ui.auth.ui.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by futzm on 11/14/2017.
 */

public class CreateAccountActivity extends AppCompatActivity {
    EditText createUsernameText;
    EditText createPasswordText;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    DatabaseReference myRef;
    private static final int SIGN_IN = 120;
    private static final String EMAIL_INVALID =  "email is invalid :";
    private static final String ACCOUNT_CREATE_SUCCESS =  "Account creation successful";
    private static final String ACCOUNT_CREATE_ERROR =  "Account creation error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setEnterTransition(slide);
        //FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        createUsernameText = (EditText)findViewById(R.id.create_username_field);
        createPasswordText = (EditText)findViewById(R.id.create_password_field);
        Button createButton = (Button) findViewById(R.id.create_account_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    public void displaySnckBar(String s){
        Snackbar snackbar = Snackbar.make(createUsernameText,s,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    protected void onActivityResult(int requestCode, int reply, Intent data) {
        super.onActivityResult(requestCode, reply, data);
        if (requestCode == SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (reply == ResultCodes.OK) {

               Intent myIntent = new Intent(CreateAccountActivity.this, NavigationActivity.class);
                displaySnckBar("Sign in successful");
                startActivity(myIntent);

                return;
            } else {
                if (response == null) {
                    displaySnckBar("Sign in got cancelled");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    displaySnckBar("Network connnection not available");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    displaySnckBar("Error occured");
                    return;
                }
            }

            displaySnckBar("Error occured");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            createUsernameText.setError(EMAIL_INVALID + email);
            return false;
        }
        return true;
    }

    public void createUser() {
        if(createUsernameText.getText() == null ||  !isEmailValid(createUsernameText.getText().toString())) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(createUsernameText.getText().toString(),createPasswordText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Snackbar snackbar = Snackbar.make(createUsernameText, ACCOUNT_CREATE_SUCCESS, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    addInUserTable(firebaseAuth);
                    Intent myIntent = new Intent(CreateAccountActivity.this, NavigationActivity.class);
                    startActivity(myIntent);

                }else{
                    Snackbar snackbar = Snackbar.make(createUsernameText, ACCOUNT_CREATE_ERROR, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }

    public void addInUserTable(FirebaseAuth firebaseAuth){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String email=firebaseUser.getEmail();
        String userName=email.replace(".","_");
        myRef=FirebaseDatabase.getInstance().getReference("Users");
        Log.d("myRef",myRef.toString());
        DatabaseReference childRef=myRef.child(userName);
        Users user=new Users();
        user.setProgramId(0);
        childRef.setValue(user);
        Log.d("users table inserted","abc");
    }
}
