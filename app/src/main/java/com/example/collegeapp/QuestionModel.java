package com.example.collegeapp;


public class QuestionModel {
    private String id;


    private String question;
    private String A;
    private String B;
    private String C;
    private String D;
    private String answer;
    private String  set;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public QuestionModel(String id, String question, String a, String b, String c, String d, String answer, String set) {
        this.id = id;
        this.question = question;
        A = a;
        B = b;
        C = c;
        D = d;
        this.answer = answer;
        this.set = set;
    }
}
