package com.example.loginapp.Activities.Model;

public class Conversation {
    public String Chinese_conversation;
    public String english_words1;

    public Conversation(){

    }

    public Conversation (String Chinese_conversation,String english_words1) {
        this.Chinese_conversation = Chinese_conversation;
        this.english_words1 = english_words1;

    }
    public String getChinese_conversation() {
        return Chinese_conversation;
    }

    public void setChinese_conversation(String chinese_conversation) { this.Chinese_conversation = Chinese_conversation;
    }

    public String getEnglish_words1() {
        return getEnglish_words1();
    }

    public void setEnglish_words1(String english_words1) {
        this.english_words1 = english_words1;
    }
}
