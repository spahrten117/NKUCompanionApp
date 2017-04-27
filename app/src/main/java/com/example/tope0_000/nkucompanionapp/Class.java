package com.example.tope0_000.nkucompanionapp;

/**
 * Created by espahr on 4/26/17.
 */

public class Class {

    private String name;
    private String grade;
    private int credits;

    public Class(String name, String grade, int credits){
        this.name = name;
        this.grade = grade;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
