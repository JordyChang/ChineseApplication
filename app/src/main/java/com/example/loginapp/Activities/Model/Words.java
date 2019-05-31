package com.example.loginapp.Activities.Model;

public class Words {
    public String chinese_words;
    public String english_words;

    public Words(){

    }

    public Words (String chinese_words,String english_words) {
        this.chinese_words = chinese_words;
        this.english_words = english_words;

    }
    public String getChinese_words() {
        return chinese_words;
    }

    public void setChinese_words(String chinese_words) {
        this.chinese_words = chinese_words;
    }

    public String getEnglish_words() {
        return english_words;
    }

    public void setEnglish_words(String english_words) {
        this.english_words = english_words;
    }
}


