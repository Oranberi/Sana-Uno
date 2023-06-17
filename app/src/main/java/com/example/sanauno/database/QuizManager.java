package com.example.sanauno.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuizManager {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public QuizManager(Context c){
        this.context = c;
    }

    public QuizManager openDB() throws SQLException {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void insertWork(String title, int lrn, int insID, int item, int score, String show){
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.CLM_QUIZTITLE, title);
        cv.put(dbHelper.CLM_QUIZLRN, lrn);
        cv.put(dbHelper.CLM_QUIZINSTRUCTOR, insID);
        cv.put(dbHelper.CLM_QUIZITEM, item);
        cv.put(dbHelper.CLM_QUIZSCORE,score);
        cv.put(dbHelper.CLM_QUIZSHOW, show);

        long result = sqLiteDatabase.insert(dbHelper.TBL_QUIZ, null, cv);
    }

    public void updateWork(String row_id, int score, String show){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        ContentValues cv =  new ContentValues();

        cv.put(dbHelper.CLM_QUIZSCORE, score);
        cv.put(dbHelper.CLM_QUIZSHOW, show);

        sqLiteDatabase.update(dbHelper.TBL_QUIZ, cv, dbHelper.CLM_QUIZID + "=?", new String[]{row_id});
    }

    public Cursor fecthAllQuiz(int instructorClass_ID){
        String[] clm = new String[] {dbHelper.CLM_QUIZID, dbHelper.CLM_QUIZTITLE, dbHelper.CLM_QUIZLRN, dbHelper.CLM_QUIZINSTRUCTOR, dbHelper.CLM_QUIZSCORE, dbHelper.CLM_QUIZITEM};
        //String[] selectionArgs =  new String[]{instructorClass_ID};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_ASSESS, clm, dbHelper.CLM_ASSESSINSTRUCTOR+"=?", selectionArgs, null, null, null);
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_QUIZ + " WHERE " + dbHelper.CLM_QUIZINSTRUCTOR + "=" + instructorClass_ID, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fecthStuQuiz(int instructorClass_ID, int lrn){
        String[] clm = new String[] {dbHelper.CLM_QUIZID, dbHelper.CLM_QUIZTITLE, dbHelper.CLM_QUIZLRN, dbHelper.CLM_QUIZINSTRUCTOR, dbHelper.CLM_QUIZSCORE, dbHelper.CLM_QUIZITEM};
        //int[] selectionArgs =  new int[]{instructorClass_ID};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_ASSESS, clm, dbHelper.CLM_ASSESSINSTRUCTOR+"=?", selectionArgs, null, null, null);
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_QUIZ + " WHERE " + dbHelper.CLM_QUIZINSTRUCTOR + "=" + instructorClass_ID + " AND " + dbHelper.CLM_QUIZLRN + "=" + lrn, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List<Integer> studentQuizScores(int instructorClass_ID, int lrn){
        List<Integer> allQuiz = new ArrayList<>();
        DBHelper myDB = new DBHelper(context.getApplicationContext());
        sqLiteDatabase = myDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + dbHelper.CLM_QUIZSCORE + " FROM " + dbHelper.TBL_QUIZ + " WHERE " + dbHelper.CLM_QUIZINSTRUCTOR + "=" + instructorClass_ID + " AND " + dbHelper.CLM_QUIZLRN + "=" + lrn, null);

        if(cursor.moveToFirst()){
            do {
                allQuiz.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        return allQuiz;
    }

    public List<Integer> studentQuizItems(int instructorClass_ID, int lrn){
        List<Integer> allQuizItems = new ArrayList<>();
        DBHelper myDB = new DBHelper(context.getApplicationContext());
        sqLiteDatabase = myDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + dbHelper.CLM_QUIZITEM + " FROM " + dbHelper.TBL_QUIZ + " WHERE " + dbHelper.CLM_QUIZINSTRUCTOR + "=" + instructorClass_ID + " AND " + dbHelper.CLM_QUIZLRN + "=" + lrn, null);

        if(cursor.moveToFirst()){
            do {
                allQuizItems.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        return allQuizItems;
    }

}