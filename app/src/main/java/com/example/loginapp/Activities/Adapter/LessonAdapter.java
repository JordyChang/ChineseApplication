package com.example.loginapp.Activities.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.loginapp.Activities.MainMenuActivity;
import com.example.loginapp.Activities.Model.Lesson;
import com.example.loginapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<Lesson> items;

    public LessonAdapter(ArrayList<Lesson> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_name, parent, false);
        return new ViewHolder(v);
    }

    private long value = 0;

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Lesson item = items.get(position);
        holder.all_user_name.setText(item.getLesson_name());
        Picasso.get().load(item.getLesson_image()).into(holder.img_lesson);
        holder.ll_row.setOnClickListener(v->{
            Intent intent = new Intent(mContext, MainMenuActivity.class);
            intent.putExtra("lesson_name", item.getLesson_name());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView all_user_name;
        public ImageView img_lesson;
        public LinearLayout ll_row;

        public ViewHolder(View v) {
            super(v);
            all_user_name = v.findViewById(R.id.all_user_name);
            img_lesson = v.findViewById(R.id.img_lesson);
            ll_row = v.findViewById(R.id.ll_row);
        }
    }
}