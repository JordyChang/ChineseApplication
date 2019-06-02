package com.example.loginapp.Activities.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.Activities.ExerciseActivity;
import com.example.loginapp.Activities.Model.Quiz;
import com.example.loginapp.R;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    private ArrayList<Quiz> items;
    Context context;

    public QuizAdapter(ArrayList<Quiz> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exercise_item, viewGroup, false);
        return new QuizAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Quiz item = items.get(i);
        viewHolder.tv_question.setText(item.getQuestion());
        viewHolder.rb_answer.setText(item.getAnswers());
        viewHolder.rb_wrong1.setText(item.getWrong1());
        viewHolder.rb_wrong2.setText(item.getWrong2());
        viewHolder.rb_wrong3.setText(item.getWrong3());
        viewHolder.rb_answer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                ((ExerciseActivity) context).checkTrue = true;
            }
        });
        viewHolder.rb_wrong1.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                ((ExerciseActivity) context).checkTrue = false;
            }
        }));
        viewHolder.rb_wrong2.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                ((ExerciseActivity) context).checkTrue = false;
            }
        }));
        viewHolder.rb_wrong3.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                ((ExerciseActivity) context).checkTrue = false;
            }
        }));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_question;
        public RadioButton rb_answer;
        public RadioButton rb_wrong1;
        public RadioButton rb_wrong2;
        public RadioButton rb_wrong3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            rb_answer = itemView.findViewById(R.id.rb_answer);
            rb_wrong1 = itemView.findViewById(R.id.rb_wrong1);
            rb_wrong2 = itemView.findViewById(R.id.rb_wrong2);
            rb_wrong3 = itemView.findViewById(R.id.rb_wrong3);
        }
    }
}
