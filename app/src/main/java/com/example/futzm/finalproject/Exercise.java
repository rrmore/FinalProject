package com.example.futzm.finalproject;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by futzm on 11/23/2017.
 */

public class Exercise {

    String name;
    String day;
    String description;
    String image1;
    String image2;
    int programId;
    int  reps;
    int sets;
    static List<Map<String, ?>> exerciseList=new ArrayList<Map<String,?>>();;
    DatabaseReference mRef;
    ExercisesRvAdapter exerciseRvAdapter;
    Context mContext;

    public Exercise(String day, String description, String image1, String image2, int programId, int reps, int sets) {
        this.day = day;
        this.description = description;
        this.image1 = image1;
        this.image2 = image2;
        this.programId = programId;
        this.reps = reps;
        this.sets = sets;
    }

    List<Map<String, ?>> getProgramsList() {
        return exerciseList;
    }

    Exercise(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public static List<Map<String, ?>> getExerciseList() {
        return exerciseList;
    }

    public static void setExerciseList(List<Map<String, ?>> exerciseList) {
        Exercise.exerciseList = exerciseList;
    }

    public int getSize(){
        return exerciseList.size();
    }

    public void setAdapter(ExercisesRvAdapter mAdapter) {
        exerciseRvAdapter = mAdapter;
    }
}
