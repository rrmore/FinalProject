package com.example.futzm.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import static com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Prompt.SIGN_IN;

public class MainActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    FirebaseAuth mAuth;
    Animation animation;
    Button login;
    Button create;
    ImageView image;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.login);
        mAuth=FirebaseAuth.getInstance();
        login=(Button) findViewById(R.id.login_button);
        create=(Button) findViewById(R.id.sign_up_button);
        image=(ImageView)findViewById(R.id.imageView);
        animation= AnimationUtils.loadAnimation(this,R.anim.slide_up);
        overridePendingTransition(R.anim.slide_right,R.anim.slide_left);
        login.setAnimation(animation);
        create.setAnimation(animation);
        animation= AnimationUtils.loadAnimation(this,R.anim.slide_down);
        image.setAnimation(animation);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    Intent myIntent = new Intent(getBaseContext(), NavigationActivity.class);  //Replace MainActivity.class with your launcher class from previous assignments
                    startActivity(myIntent);
                }
                else {
                }
            }
        };
        usernameText = (EditText)findViewById(R.id.user_name);
        passwordText = (EditText)findViewById(R.id.password);
        //final String userName=usernameText.getText().toString();
        //final String password=passwordText.getText().toString();
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=usernameText.getText().toString();
                String password=passwordText.getText().toString();
                AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false);
                login(userName,password);

            }
        });
        Button createAccountButton= (Button)findViewById(R.id.sign_up_button);
        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    public void displaySnckBar(String s){
        Snackbar snackbar = Snackbar.make(usernameText,s,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void login(String userName,String password){
        mAuth.signInWithEmailAndPassword(userName,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //processDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            finish();
                            startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
                        }
                        else{
                            displaySnckBar("Authentication failed. Please check username or password");
                        }
                    }
                });
    }

    protected void onActivityResult(int requestCode, int reply, Intent data) {
        super.onActivityResult(requestCode, reply, data);
        if (requestCode == SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (reply == ResultCodes.OK) {

                Intent myIntent = new Intent(MainActivity.this, NavigationActivity.class);
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
//        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }
}
