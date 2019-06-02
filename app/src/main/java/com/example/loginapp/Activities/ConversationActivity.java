package com.example.loginapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.Activities.Adapter.ConversationAdapter;
import com.example.loginapp.Activities.Adapter.WordsAdapter;
import com.example.loginapp.Activities.Model.Conversation;
import com.example.loginapp.Activities.Model.Lesson;
import com.example.loginapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationActivity extends AppCompatActivity {
    @BindView(R.id.toolbarConv)
    Toolbar toolbarConv;
    @BindView(R.id.rv_conversation_list)
    RecyclerView rv_conversation_list;
    private String lesson_name;

    private ArrayList<Conversation> conversationList = new ArrayList<>();
    private ConversationAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        getExtrasIntent();
        setUI();
    }

    private void setUI() {
        toolbarConv.setTitle(lesson_name + " - Conversation");
        toolbarConv.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarConv);
        rv_conversation_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_conversation_list.setLayoutManager(layoutManager);
        mAdapter = new ConversationAdapter(conversationList, getApplicationContext());
        rv_conversation_list.setAdapter(mAdapter);
        loadConv();
    }

    private void loadConv() {
        BaseApp.db
                .collection("lesson")
                .whereEqualTo("lesson_name", lesson_name.trim())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    conversationList.clear();
                    Toast.makeText(this, queryDocumentSnapshots.size()+"", Toast.LENGTH_SHORT).show();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Map<String, Object> words = (Map<String, Object>) documentSnapshot.getData().get("conversation");
                        String pinyin = words.get("chinese_conv_pinyin").toString();
                        String chinese = words.get("chinese_conversation").toString();
                        String english = words.get("english_conversation").toString();
                        String audio = words.get("conversation_voice").toString();
                        Conversation word = new Conversation(pinyin, chinese, audio, english);
                        conversationList.add(word);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getExtrasIntent() {
        lesson_name = getIntent().getStringExtra("lesson_name");
        Toast.makeText(this, lesson_name, Toast.LENGTH_SHORT).show();
    }

}
