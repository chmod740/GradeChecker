package com.imudgesyy.GradeChecker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yangyang on 2016/6/28.
 */
public class DataUtil {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void saveStudentInfo(StudentModel studentModel, Context context){
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("config", Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        try{
            editor.putString("username",studentModel.getUsername());
            editor.putString("password",studentModel.getPassword());
            editor.commit();
        }catch (Exception e){

        }
    }

    public static StudentModel getStudentInfo(Context context){
        StudentModel studentModel = new StudentModel();
        if (sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("config", Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        studentModel.setUsername(sharedPreferences.getString("username",""));
        studentModel.setPassword(sharedPreferences.getString("password",""));
        return studentModel;
    }
}
