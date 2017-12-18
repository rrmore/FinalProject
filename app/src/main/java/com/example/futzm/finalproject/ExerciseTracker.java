package com.example.futzm.finalproject;

/**
 * Created by futzm on 11/23/2017.
 */

public class ExerciseTracker {
    public ExerciseTracker(String exercise, String day, String status) {
        this.exercise = exercise;
        this.day = day;
        this.status = status;
    }

    public ExerciseTracker(String exercise, String day) {
        this.exercise = exercise;
        this.day = day;
        status="no";
    }

    public ExerciseTracker() {
    }


    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String exercise;
    String day;
    String status;

}
