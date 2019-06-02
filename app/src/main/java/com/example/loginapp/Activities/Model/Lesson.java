package com.example.loginapp.Activities.Model;

public class Lesson {
    public String lesson_name;
    public String lesson_image;

    public Lesson(){

    }

    public Lesson(String lesson_name,String lesson_image) {
        this.lesson_name = lesson_name;
        this.lesson_image = lesson_image;

    }
    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public String getLesson_image() {
        return lesson_image;
    }

    public void setLesson_image(String lesson_image) {
        this.lesson_image = lesson_image;
    }
}
