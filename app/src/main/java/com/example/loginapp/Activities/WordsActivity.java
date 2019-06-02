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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.Activities.Adapter.WordsAdapter;
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
import com.google.firebase.firestore.DocumentSnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordsActivity extends AppCompatActivity {
    @BindView(R.id.toolbarWords)
    Toolbar toolbarWords;
    @BindView(R.id.rv_words_list)
    RecyclerView rv_words_list;
    public String lesson_name;

    private ArrayList<Words> wordsList = new ArrayList<>();
    private WordsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        ButterKnife.bind(this);
        getExtrasIntent();
        setUI();
    }

    private void setUI() {
        toolbarWords.setTitle(lesson_name);
        toolbarWords.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarWords);
        rv_words_list.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2, RecyclerView.VERTICAL, false);
        rv_words_list.setLayoutManager(layoutManager);
        mAdapter = new WordsAdapter(wordsList, getApplicationContext());
        rv_words_list.setAdapter(mAdapter);
        loadWords();
    }

    private void loadWords() {
        BaseApp.db
                .collection("lesson")
                .whereEqualTo("lesson_name", lesson_name.trim())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    wordsList.clear();
                    Toast.makeText(this, queryDocumentSnapshots.size()+"", Toast.LENGTH_SHORT).show();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Map<String, Object> words = (Map<String, Object>) documentSnapshot.getData().get("words");
                        String pinyin = words.get("chinese_words").toString();
                        String english = words.get("english_words").toString();
                        String img = words.get("words_image").toString();
                        String audio = words.get("words_voice").toString();
                        Words word = new Words(pinyin, english, img, audio);
                        Toast.makeText(this, lesson_name, Toast.LENGTH_SHORT).show();
                        wordsList.add(word);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getExtrasIntent() {
        lesson_name = getIntent().getStringExtra("lesson_name");
        Toast.makeText(this, lesson_name, Toast.LENGTH_SHORT).show();
    }
}
