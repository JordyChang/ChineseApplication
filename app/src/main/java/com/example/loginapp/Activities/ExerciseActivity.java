package com.example.loginapp.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.Activities.Adapter.QuizAdapter;
import com.example.loginapp.Activities.Model.Quiz;
import com.example.loginapp.Activities.Model.Words;
import com.example.loginapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbarExercise)
    Toolbar toolbarExercise;
    @BindView(R.id.rv_quiz_list)
    RecyclerView rv_quiz_list;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    public String lesson_name;
    private ArrayList<Quiz> quizList = new ArrayList<>();
    private QuizAdapter mAdapter;

    public boolean checkTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);
        getExtrasIntent();
        setUI();
    }

    private void setUI() {
        btn_submit.setOnClickListener(this);
        toolbarExercise.setTitle(lesson_name + " - Quiz");
        toolbarExercise.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarExercise);
        rv_quiz_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_quiz_list.setLayoutManager(layoutManager);
        mAdapter = new QuizAdapter(quizList, this);
        rv_quiz_list.setAdapter(mAdapter);
        loadQuiz();
    }

    private void loadQuiz() {
        BaseApp.db
                .collection("lesson")
                .whereEqualTo("lesson_name", lesson_name.trim())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    quizList.clear();
                    Toast.makeText(this, queryDocumentSnapshots.size()+"", Toast.LENGTH_SHORT).show();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Map<String, Object> words = (Map<String, Object>) documentSnapshot.getData().get("questions");
                        String question = words.get("questions").toString();
                        String answer = words.get("answers").toString();
                        String wrong1 = words.get("wrong1").toString();
                        String wrong2 = words.get("wrong2").toString();
                        String wrong3 = words.get("wrong3").toString();
                        Quiz word = new Quiz(question, answer, wrong1, wrong2, wrong3);
                        quizList.add(word);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getExtrasIntent() {
        lesson_name = getIntent().getStringExtra("lesson_name");
        Toast.makeText(this, lesson_name, Toast.LENGTH_SHORT).show();
    }

    private void successDialog() {
        Dialog addPondDialog = new Dialog(this);
        addPondDialog.setContentView(R.layout.dialog_success);
        addPondDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addPondDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addPondDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                if(checkTrue){
                    successDialog();
                } else {
                    Toast.makeText(this, "Check on your answers again", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
