package com.example.loginapp.Activities.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.Activities.Model.Conversation;
import com.example.loginapp.Activities.Model.Words;
import com.example.loginapp.R;

import java.io.IOException;
import java.util.ArrayList;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private ArrayList<Conversation> items;
    private Context mContext;

    public ConversationAdapter(ArrayList<Conversation> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.conversation_item, viewGroup, false);
        return new ConversationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Conversation item = items.get(i);
        viewHolder.tv_pinyin.setText(item.getChinese_conv_pinyin());
        viewHolder.tv_chinese.setText(item.getChinese_conversation());
        viewHolder.tv_english.setText(item.getEnglish_conversation());
        viewHolder.audioButton.setOnClickListener(v -> {
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(item.getConversation_voice());
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

        public TextView tv_pinyin;
        public TextView tv_chinese;
        public TextView tv_english;
        public ImageButton audioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pinyin = itemView.findViewById(R.id.tv_pinyin);
            tv_chinese = itemView.findViewById(R.id.tv_chinese);
            tv_english = itemView.findViewById(R.id.tv_english);
            audioButton = itemView.findViewById(R.id.audioButton);
        }
    }
}
