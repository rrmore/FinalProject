package com.example.futzm.finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by futzm on 11/25/2017.
 */

public class ProgramsData {

     static List<Map<String, ?>> programsList;

    List<Map<String, ?>> getProgramsList() {
        return programsList;
    }

    public int getSize() {
        return programsList.size();
    }

    ProgramsData(){
        int programId;
        int image;
        String programName;
        programsList = new ArrayList<Map<String, ?>>();

        //1
        programId=1;
        image=R.drawable.amateur;
        programName="Begineer";
        programsList.add(createProgram(programId,programName,image));

        programId=2;
        image=R.drawable.intermediate;
        programName="Intermediate";
        programsList.add(createProgram(programId,programName,image));

        programId=3;
        image=R.drawable.pro;
        programName="Professional";
        programsList.add(createProgram(programId,programName,image));

    }

    public static HashMap getItem(int i) {
        if (i >= 0 && i < programsList.size()) {
            return (HashMap) programsList.get(i);
        } else
            return null;
    }

    private HashMap createProgram(int programId,String programName,int image) {
        HashMap program = new HashMap();
        program.put("programId",programId);
        program.put("programName", programName);
        program.put("image", image);
        return program;
    }

}
