package com.example.loginapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.CircularPropagation;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.example.loginapp.Activities.Model.Conversation;
import com.example.loginapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.Wordsbtn)
    Button Wordsbtn;
    @BindView(R.id.convbtn)
    ImageButton convbtn;
    @BindView(R.id.exercisebtn)
    ImageButton exercisebtn;

    private FirebaseAuth mAuth;
    private android.support.v7.widget.Toolbar mToolbar;
    public String lesson_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        final Button Wordsbtn = findViewById(R.id.Wordsbtn);
        final ImageButton convbtn = findViewById(R.id.convbtn);
        final ImageButton exercisebtn = findViewById(R.id.exercisebtn);
        mAuth = FirebaseAuth.getInstance();
        getExtrasIntent();
        setUI();
    }

    private void setUI() {
        Wordsbtn.setOnClickListener(this);
        convbtn.setOnClickListener(this);
        exercisebtn.setOnClickListener(this);
    }

    private void getExtrasIntent() {
        lesson_name = getIntent().getStringExtra("lesson_name");
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.Wordsbtn:
                intent = new Intent(MainMenuActivity.this, WordsActivity.class);
                intent.putExtra("lesson_    name", lesson_name);
                startActivity(intent);
                break;
            case R.id.convbtn:
                intent = new Intent(MainMenuActivity.this, ConversationActivity.class);
                startActivity(intent);
                break;
            case R.id.exercisebtn:
                intent = new Intent(MainMenuActivity.this, ExerciseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
