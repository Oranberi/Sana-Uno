package com.example.sanauno.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AssessManager {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public AssessManager(Context c){
        this.context = c;
    }

    public AssessManager openDB() throws SQLException {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void insertWork(String title, int lrn, int insID, int item, int score, String show){
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.CLM_ASSESSTITLE, title);
        cv.put(dbHelper.CLM_ASSESSLRN, lrn);
        cv.put(dbHelper.CLM_ASSESSINSTRUCTOR, insID);
        cv.put(dbHelper.CLM_ASSESSITEM, item);
        cv.put(dbHelper.CLM_ASSESSSCORE,score);
        cv.put(dbHelper.CLM_ASSESSSHOW, show);

        long result = sqLiteDatabase.insert(dbHelper.TBL_ASSESS, null, cv);
    }

    public void updateWork(String row_id, int score, String show){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        ContentValues cv =  new ContentValues();

        cv.put(dbHelper.CLM_ASSESSSCORE, score);
        cv.put(dbHelper.CLM_ASSESSSHOW, show);

        sqLiteDatabase.update(dbHelper.TBL_ASSESS, cv, dbHelper.CLM_ASSESSID + "=?", new String[]{row_id});
    }

    public Cursor fecthAllAssessment(int instructorClass_ID){
        String[] clm = new String[] {dbHelper.CLM_ASSESSID, dbHelper.CLM_ASSESSTITLE, dbHelper.CLM_ASSESSLRN, dbHelper.CLM_ASSESSINSTRUCTOR, dbHelper.CLM_ASSESSSCORE, dbHelper.CLM_ASSESSITEM};
        //String[] selectionArgs =  new String[]{instructorClass_ID};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_ASSESS, clm, dbHelper.CLM_ASSESSINSTRUCTOR+"=?", selectionArgs, null, null, null);
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_ASSESS + " WHERE " + dbHelper.CLM_ASSESSINSTRUCTOR + "=" + instructorClass_ID, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fecthStuAssessment(int instructorClass_ID, int lrn){
        String[] clm = new String[] {dbHelper.CLM_ASSESSID, dbHelper.CLM_ASSESSTITLE, dbHelper.CLM_ASSESSLRN, dbHelper.CLM_ASSESSINSTRUCTOR, dbHelper.CLM_ASSESSSCORE, dbHelper.CLM_ASSESSITEM};
        //int[] selectionArgs =  new int[]{instructorClass_ID};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_ASSESS, clm, dbHelper.CLM_ASSESSINSTRUCTOR+"=?", selectionArgs, null, null, null);
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_ASSESS + " WHERE " + dbHelper.CLM_ASSESSINSTRUCTOR + "=" + instructorClass_ID + " AND " + dbHelper.CLM_ASSESSLRN + "=" + lrn, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List<Integer> studentAssessmentScores(int instructorClass_ID, int lrn){
        List<Integer> allAssess = new ArrayList<>();
        DBHelper myDB = new DBHelper(context.getApplicationContext());
        sqLiteDatabase = myDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + dbHelper.CLM_ASSESSSCORE + " FROM " + dbHelper.TBL_ASSESS + " WHERE " + dbHelper.CLM_ASSESSINSTRUCTOR + "=" + instructorClass_ID + " AND " + dbHelper.CLM_ASSESSLRN + "=" + lrn, null);

        if(cursor.moveToFirst()){
            do {
                allAssess.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        return allAssess;
    }

    public List<Integer> studentAssessmentItems(int instructorClass_ID, int lrn){
        List<Integer> allAssessItems = new ArrayList<>();
        DBHelper myDB = new DBHelper(context.getApplicationContext());
        sqLiteDatabase = myDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + dbHelper.CLM_ASSESSITEM + " FROM " + dbHelper.TBL_ASSESS + " WHERE " + dbHelper.CLM_ASSESSINSTRUCTOR + "=" + instructorClass_ID + " AND " + dbHelper.CLM_ASSESSLRN + "=" + lrn, null);

        if(cursor.moveToFirst()){
            do {
                allAssessItems.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        return allAssessItems;
    }

}
