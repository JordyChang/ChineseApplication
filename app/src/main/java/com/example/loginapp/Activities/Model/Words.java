package com.example.loginapp.Activities.Model;

public class Words {
    public String chinese_words;
    public String english_words;
    public String words_image;
    public String words_voice;

    public Words(){
    }

    public Words(String chinese_words, String english_words, String words_image, String words_voice) {
        this.chinese_words = chinese_words;
        this.english_words = english_words;
        this.words_image = words_image;
        this.words_voice = words_voice;
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

    public String getWords_image() {
        return words_image;
    }

    public void setWords_image(String words_image) {
        this.words_image = words_image;
    }

    public String getWords_voice() {
        return words_voice;
    }

    public void setWords_voice(String words_voice) {
        this.words_voice = words_voice;
    }
}


