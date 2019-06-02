package com.example.loginapp.Activities;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText UserEmail,UserPassword,UserPassword2, UserName;
    private ProgressBar LoadingProgress;
    private Button regBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UserEmail = findViewById(R.id.regMail);
        UserPassword = findViewById(R.id.regPassword);
        UserPassword2 = findViewById(R.id.regPassword2);
        UserName = findViewById(R.id.regName);
        LoadingProgress = findViewById(R.id.regProgressBar);
        regBtn = findViewById(R.id.regBtn);
        LoadingProgress.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(i);

                regBtn.setVisibility(View.INVISIBLE);
                LoadingProgress.setVisibility(View.VISIBLE);
                final String email = UserEmail.getText().toString();
                final String password = UserPassword.getText().toString();
                final String password2 = UserPassword2.getText().toString();
                final String name = UserName.getText().toString();

                if (email.isEmpty() || name.isEmpty() || password.isEmpty() || password2.isEmpty() || !password.equals(password2)){
                    // something goes wrong : all fields must be filled
                    // we need to display an error message
                    ShowMessage("Please verify all fields");
                    regBtn.setVisibility(View.VISIBLE);
                    LoadingProgress.setVisibility(View.INVISIBLE);

                }
                else{
                    //everything is ok and all fields are filled now we can start creating user account
                    //CreateUserAccount method will try to create the user if the email is valid

                    CreateUserAccount(email,name,password);
                }
            }
        });



    }

    private void CreateUserAccount(String email, String name, String password) {


        // this method create user account with specific email and password

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // user account created successfully
                            showMessage("Account created");
                         }
                        else
                            {

                            // account creation failed
                            showMessage("account creation failed" + task.getException().getMessage());
                            regBtn.setVisibility(View.VISIBLE);
                            LoadingProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void showMessage(String account_created) {
    }


    //simple method to show toast message
    private void ShowMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

