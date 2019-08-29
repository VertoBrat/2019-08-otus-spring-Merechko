package ru.phororex.hw1.model;


public class Question {

    private String theQuestion;
    private String answer;

    public Question() {
    }

    public Question(String theQuestion, String answer) {
        this.theQuestion = theQuestion;
        this.answer = answer;
    }

    public String getTheQuestion() {
        return theQuestion;
    }

    public void setTheQuestion(String theQuestion) {
        this.theQuestion = theQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "question='" + theQuestion + '\'' +
                ", answer='" + answer + '\'';
    }
}
