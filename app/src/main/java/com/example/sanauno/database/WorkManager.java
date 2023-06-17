package com.example.sanauno.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WorkManager {

    /**private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public WorkManager(Context c){
        this.context = c;
    }

    public WorkManager openDB() throws SQLException {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }
    public void closeDB(){
        dbHelper.close();
    }

    public void insertWork(String title, String category, String subjectName, int item, int score, int studentLRN, int instructorClassID, String show){
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.CLM_TITLE, title);
        cv.put(dbHelper.CLM_CATEGORY, category);
        cv.put(dbHelper.CLM_SUBJECT, subjectName);
        cv.put(dbHelper.CLM_ITEM, item);
        cv.put(dbHelper.CLM_SCORE, score);
        cv.put(dbHelper.CLM_WLRN, studentLRN);
        cv.put(dbHelper.CLM_WRINSTRUCTOR, instructorClassID);
        cv.put(dbHelper.CLM_WSHOW, show);

        long result = sqLiteDatabase.insert(dbHelper.TBL_WORK, null, cv);
    }

    public void updateWork(String row_id, int score, String show){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        ContentValues cv =  new ContentValues();

        cv.put(dbHelper.CLM_SCORE, score);
        cv.put(dbHelper.CLM_WSHOW, show);

        sqLiteDatabase.update(dbHelper.TBL_WORK, cv, dbHelper.CLM_WID + "=?", new String[]{row_id});
    }

    public void delete_User(String row_id){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        sqLiteDatabase.delete(dbHelper.TBL_WORK, dbHelper.CLM_WID + "=?", new String[]{row_id});
    }

    public Cursor fetchforTeacher(int instructorClassID){
        String[] clm = new String[]{dbHelper.CLM_WID, dbHelper.CLM_TITLE, dbHelper.CLM_CATEGORY, dbHelper.CLM_ITEM, dbHelper.CLM_SCORE, dbHelper.CLM_SUBJECT, dbHelper.CLM_WLRN, dbHelper.CLM_WSHOW};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_WORK + " WHERE " + dbHelper.CLM_WRINSTRUCTOR + "=" + instructorClassID, null);
        //String[] selectionArgs = new String[]{instructorClassID};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_INSTRUCTOR, clm, dbHelper.CLM_WRINSTRUCTOR +" =?", null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchforStudent(){
        Cursor cursor =;
        return cursor;
    }**/


}
