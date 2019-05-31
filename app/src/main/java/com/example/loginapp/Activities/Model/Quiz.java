package com.example.loginapp.Activities.Model;

public class Quiz {
    public String question;
    public String Answers_user;
    public String Correct_answers;

    public Quiz (){

    }

    public Quiz (String question,String Answers_user,String Correct_answers) {
        this.question = question;
        this.Answers_user= Answers_user;
        this.Correct_answers= Correct_answers;

    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswers_user() { return getAnswers_user();
    }

    public void setAnswers_user(String Answers_user) {
        this.Answers_user = Answers_user;
    }

    public String getCorrect_answers() {return Correct_answers;}

    public void setCorrect_answers(String correct_answers) {
        Correct_answers = correct_answers; }
}
