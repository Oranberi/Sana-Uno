package com.example.sanauno.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExamManager {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public ExamManager(Context c){
        this.context = c;
    }

    public ExamManager openDB() throws SQLException {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void insertWork(String title, int lrn, int insID, int item, int score, String show){
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.CLM_EXAMTITLE, title);
        cv.put(dbHelper.CLM_EXAMLRN, lrn);
        cv.put(dbHelper.CLM_EXAMINSTRUCTOR, insID);
        cv.put(dbHelper.CLM_EXAMITEM, item);
        cv.put(dbHelper.CLM_EXAMSCORE,score);
        cv.put(dbHelper.CLM_EXAMSHOW, show);

        long result = sqLiteDatabase.insert(dbHelper.TBL_EXAM, null, cv);
    }

    public void updateWork(String row_id, int score, String show){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        ContentValues cv =  new ContentValues();

        cv.put(dbHelper.CLM_EXAMSCORE, score);
        cv.put(dbHelper.CLM_EXAMSHOW, show);

        sqLiteDatabase.update(dbHelper.TBL_EXAM, cv, dbHelper.CLM_EXAMID + "=?", new String[]{row_id});
    }

    public Cursor fecthAllExam(int instructorClass_ID){
        String[] clm = new String[] {dbHelper.CLM_EXAMID, dbHelper.CLM_EXAMTITLE, dbHelper.CLM_EXAMLRN, dbHelper.CLM_EXAMINSTRUCTOR, dbHelper.CLM_EXAMSCORE, dbHelper.CLM_EXAMITEM};
        //String[] selectionArgs =  new String[]{instructorClass_ID};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_ASSESS, clm, dbHelper.CLM_ASSESSINSTRUCTOR+"=?", selectionArgs, null, null, null);
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_EXAM + " WHERE " + dbHelper.CLM_EXAMINSTRUCTOR + "=" + instructorClass_ID, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fecthStuExam(int instructorClass_ID, int lrn){
        String[] clm = new String[] {dbHelper.CLM_EXAMID, dbHelper.CLM_EXAMTITLE, dbHelper.CLM_EXAMLRN, dbHelper.CLM_EXAMINSTRUCTOR, dbHelper.CLM_EXAMSCORE, dbHelper.CLM_EXAMITEM};
        //int[] selectionArgs =  new int[]{instructorClass_ID};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_ASSESS, clm, dbHelper.CLM_ASSESSINSTRUCTOR+"=?", selectionArgs, null, null, null);
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_EXAM + " WHERE " + dbHelper.CLM_EXAMINSTRUCTOR + "=" + instructorClass_ID + " AND " + dbHelper.CLM_EXAMLRN + "=" + lrn, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public List<Integer> studentExamScores(int instructorClass_ID, int lrn){
        List<Integer> allExam = new ArrayList<>();
        DBHelper myDB = new DBHelper(context.getApplicationContext());
        sqLiteDatabase = myDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + dbHelper.CLM_EXAMSCORE + " FROM " + dbHelper.TBL_EXAM + " WHERE " + dbHelper.CLM_EXAMINSTRUCTOR + "=" + instructorClass_ID + " AND " + dbHelper.CLM_EXAMLRN + "=" + lrn, null);

        if(cursor.moveToFirst()){
            do {
                allExam.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        return allExam;
    }

    public List<Integer> studentExamItems(int instructorClass_ID, int lrn){
        List<Integer> allExamItems = new ArrayList<>();
        DBHelper myDB = new DBHelper(context.getApplicationContext());
        sqLiteDatabase = myDB.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + dbHelper.CLM_EXAMITEM + " FROM " + dbHelper.TBL_EXAM + " WHERE " + dbHelper.CLM_EXAMINSTRUCTOR + "=" + instructorClass_ID + " AND " + dbHelper.CLM_EXAMLRN + "=" + lrn, null);

        if(cursor.moveToFirst()){
            do {
                allExamItems.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        return allExamItems;
    }

}
