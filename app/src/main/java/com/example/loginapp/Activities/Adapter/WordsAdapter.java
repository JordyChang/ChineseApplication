package com.example.loginapp.Activities.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.Activities.Model.Words;
import com.example.loginapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

    private ArrayList<Words> items;
    private Context mContext;

    public WordsAdapter(ArrayList<Words> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wordlist, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Words item = items.get(i);
        viewHolder.pinyin.setText(item.getChinese_words());
        viewHolder.english.setText(item.getEnglish_words());
        Picasso.get().load(item.getWords_image()).into(viewHolder.imgWords);
        viewHolder.audio.setOnClickListener(v -> {
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(item.getWords_voice());
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pinyin;
        public TextView english;
        public ImageView imgWords;
        public LinearLayout lnWord1;
        public ImageButton audio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pinyin = itemView.findViewById(R.id.pinyin);
            english = itemView.findViewById(R.id.english);
            imgWords = itemView.findViewById(R.id.imgWords);
            lnWord1 = itemView.findViewById(R.id.lnWord1);
            audio = itemView.findViewById(R.id.audioButton);
        }

    }
}
