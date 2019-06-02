package com.example.loginapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loginapp.Activities.Adapter.LessonAdapter;
import com.example.loginapp.Activities.Model.Lesson;
import com.example.loginapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView list;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private ArrayList<Lesson> lessons = new ArrayList<>();
    private LessonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lesson Begins.....");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        Log.d("user_id", user_id);

        list = (RecyclerView) findViewById(R.id.SimplelistView);
        setUI();
    }

    private void setUI() {
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LessonAdapter(lessons, this);
        list.setAdapter(mAdapter);
        loadLesson();
    }

    private ArrayList<Lesson> check = new ArrayList<>();
    private boolean isLessonExists = false;

    private void loadLesson() {
        BaseApp.db
                .collection("lesson")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    try {
                        lessons.clear();

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            isLessonExists = false;
                            String lesson_name = documentSnapshot.getData().get("lesson_name").toString();
                            String lesson_img = documentSnapshot.getData().get("lesson_image").toString();
                            Lesson lesson = new Lesson(lesson_name, lesson_img);
                            //check.add(lesson);
                            for(Lesson wor : lessons){
                                if(wor.lesson_name.equals(lesson_name)){
                                    isLessonExists = true;
                                    //lessons.add(lesson);
                                }
                            }

                            if(!isLessonExists) {
                                lessons.add(lesson);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception ex) {}
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_lesson:
                Intent i = new Intent(HomePageActivity.this, AddLessonActivity.class);
                startActivity(i);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
