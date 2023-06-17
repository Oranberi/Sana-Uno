package com.example.sanauno.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class ClassManager {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public ClassManager(Context c){
        this.context = c;
    }

    public ClassManager openDB() throws SQLException {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }
    public void closeDB(){
        dbHelper.close();
    }

    public void insertClass(int Lrn, String className, String classCode, int classInstructorID, String instructorName, String show, int grade){
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.CLM_CLRN, Lrn);
        cv.put(dbHelper.CLM_CINSNAME, instructorName);
        cv.put(dbHelper.CLM_CNAME, className);
        cv.put(dbHelper.CLM_CLASSCODE, classCode);
        cv.put(dbHelper.CLM_CLINSTRUCTOR, classInstructorID);
        cv.put(dbHelper.CLM_FINALGRADE, grade);
        cv.put(dbHelper.CLM_CSHOW, show);

        long result = sqLiteDatabase.insert(dbHelper.TBL_CLASS, null, cv);
    }

    public void updateClass(String row_id, double finalGrade, String showGrade){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.CLM_FINALGRADE, finalGrade);
        cv.put(DBHelper.CLM_CSHOW, showGrade);

        sqLiteDatabase.update(dbHelper.TBL_CLASS, cv, dbHelper.CLM_CID + "=?", new String[]{row_id});
    }

    public void delete_Class(String row_id){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        sqLiteDatabase.delete(dbHelper.TBL_CLASS, dbHelper.CLM_CID + "=?", new String[]{row_id});
    }


    public Cursor fetch_ClassTeacher(String lrn){
        String[] clm = new String[]{dbHelper.CLM_CNAME, dbHelper.CLM_SEMESTER, dbHelper.CLM_FINALGRADE};
        String selection = dbHelper.CLM_LRN +" =?";
        String[] selectionArgs = new String[]{lrn};
        Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_USER, clm,selection,selectionArgs,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_ClassStudent(String lrn){
        String[] clm = new String[]{dbHelper.CLM_CID, dbHelper.CLM_CNAME, dbHelper.CLM_CCODE, dbHelper.CLM_SEMESTER, dbHelper.CLM_CLRN, dbHelper.CLM_CLINSTRUCTOR, dbHelper.CLM_CINSNAME, dbHelper.CLM_FINALGRADE, dbHelper.CLM_CSHOW};
        //String selection = dbHelper.CLM_LRN +" =?";
        String[] selectionArgs = new String[]{lrn};
        Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_CLASS, clm, dbHelper.CLM_CLRN +" =?", selectionArgs,null,null,null);
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_CLASS, clm,selection,selectionArgs,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetch_StudentEnrolled(int insID){
        //String[] clm = new String[]{dbHelper.CLM_CID, dbHelper.CLM_CNAME, dbHelper.CLM_CCODE, dbHelper.CLM_SEMESTER, dbHelper.CLM_CLRN, dbHelper.CLM_CLINSTRUCTOR, dbHelper.CLM_CINSNAME};
        //String selection = dbHelper.CLM_LRN +" =?";
        //String[] selectionArgs = new String[]{insID};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_CLASS, clm, dbHelper.CLM_CLINSTRUCTOR +" =?", selectionArgs,null,null,null);
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_CLASS, clm,selection,selectionArgs,null,null,null);
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_CLASS + " WHERE " + dbHelper.CLM_CLINSTRUCTOR + "=" + insID, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }


}
