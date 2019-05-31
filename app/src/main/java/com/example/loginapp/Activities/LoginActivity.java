package com.example.loginapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

private EditText UserEmail,UserPassword;
    private ProgressBar LoadingProgress;
    private Button signinbtn;
    private FirebaseAuth mAuth;
    private Intent LoginActivity;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = (Toolbar)findViewById(R.id.toolbar2);

        UserEmail = findViewById(R.id.Email);
        UserPassword = findViewById(R.id.Loginpassword);
        LoadingProgress = findViewById(R.id.progressBar);
        signinbtn = findViewById(R.id.SigninBtn);
        LoadingProgress.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        LoginActivity = new Intent(this, HomePageActivity.class);

        Button Signinbtn = findViewById(R.id.SigninBtn);
        Button Signupbtn = findViewById(R.id.SignupBtn);

        Signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinbtn.setVisibility(View.INVISIBLE);
                LoadingProgress.setVisibility(View.VISIBLE);
                final String email = UserEmail.getText().toString();
                final String password = UserPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    showMessage("Please Verify All Fields");
                    signinbtn.setVisibility(View.VISIBLE);
                    LoadingProgress.setVisibility(View.INVISIBLE);
                }
                else{
                    signIn(email,password);
                }
            }
        });




        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(i);

            }

        });
    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    LoadingProgress.setVisibility(View.INVISIBLE);
                    signinbtn.setVisibility(View.VISIBLE);
                    updateUI();
                }
                else{

                    showMessage(task.getException().getMessage());
                    signinbtn.setVisibility(View.VISIBLE);
                    LoadingProgress.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    private void updateUI() {

        startActivity(LoginActivity);
        finish();
    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user !=null) {
            // user is already connected so we need to redirect him to home page
            updateUI();
        }
    }
}