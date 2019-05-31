package com.example.loginapp.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginapp.Activities.Model.Lesson;
import com.example.loginapp.Activities.Model.Words;
import com.example.loginapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class WordsActivity extends AppCompatActivity {
    public String lessonID;
    private DatabaseReference wordsRef,lessonRef;
    private FirebaseAuth mAuth;
    private TextView txtpinyin,txtenglish,txtpinyin1,txtenglish1,txtpinyin2,txtenglish2,txtpinyin3,txtenglish3;
    private Toolbar toolbar;
    private ImageButton img1,img2,img3,img4;
    private ImageView imageView1;
    private RecyclerView list;

    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        toolbar = (Toolbar) findViewById(R.id.toolbarWords);
        mp = MediaPlayer.create(this, R.raw.family_dad);
        //lessonID = getIntent().getExtras().get("lessonID").toString();
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        lessonRef = FirebaseDatabase.getInstance().getReference().child("lesson");
        wordsRef  = FirebaseDatabase.getInstance().getReference().child("words");

        list = (RecyclerView) findViewById(R.id.wordlList);
        list.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        list.setLayoutManager(mLayoutManager);

    }
}
