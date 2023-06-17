package com.example.sanauno.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserManager {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public UserManager(Context c){
        this.context = c;
    }

    public UserManager openDB() throws SQLException{
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void closeDB(){
        dbHelper.close();
    }

    public void insert_User(String Name, String Email, String Password, String Role, int Lrn, int IDnum){
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.CLM_NAME, Name);
        cv.put(dbHelper.CLM_EMAIL, Email);
        cv.put(dbHelper.CLM_PASSWORD, Password);
        cv.put(dbHelper.CLM_ROLE, Role);
        cv.put(dbHelper.CLM_LRN, Lrn);
        cv.put(dbHelper.CLM_IDNUM, IDnum);

        long result = sqLiteDatabase.insert(dbHelper.TBL_USER, null, cv);
    }

    public void update_User(String row_id,String Password){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        ContentValues cv =  new ContentValues();

        cv.put(dbHelper.CLM_PASSWORD, Password);

        sqLiteDatabase.update(dbHelper.TBL_USER, cv, dbHelper.CLM_ID + "=?", new String[]{row_id});
    }

    public void delete_User(String row_id){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        sqLiteDatabase.delete(dbHelper.TBL_USER, dbHelper.CLM_ID + "=?", new String[]{row_id});
    }

    public Cursor fetch_allUser(){
        String[] clm = new String[]{dbHelper.CLM_ID, dbHelper.CLM_EMAIL, dbHelper.CLM_PASSWORD, dbHelper.CLM_ROLE, dbHelper.CLM_NAME, dbHelper.CLM_LRN};
        Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_USER, clm, null, null, null, null, null);
        return cursor;
    }

    public boolean isEmailAlreadyUsed(String userEmail){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + dbHelper.CLM_EMAIL + " FROM " + dbHelper.TBL_USER, null);
        String holdEmail = "";
        boolean isUsed = false;

        if (cursor.moveToFirst()){
            do{
                holdEmail = cursor.getString(0);
                if (holdEmail.equals(userEmail)){
                    isUsed = true;
                }
            } while (cursor.moveToNext());
        }
        return isUsed;
    }

    public Cursor fetch_StudentUser(){
        String[] clm = new String[]{dbHelper.CLM_ID, dbHelper.CLM_NAME, dbHelper.CLM_LRN, dbHelper.CLM_EMAIL};
        //String selection = dbHelper.CLM_ROLE +" =?";
        //String[] selectionArgs = new String[]{"Student"};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_USER, clm,selection,selectionArgs,null,null,null);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_USER + " WHERE " + dbHelper.CLM_ROLE + "=" + "'Student'", null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public String fecthStudentName(String lrn){
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + dbHelper.CLM_NAME + " FROM " + dbHelper.TBL_USER + " WHERE " + dbHelper.CLM_LRN
                + "='" + lrn + "'", null);
        if(cursor != null && cursor.moveToFirst()){
            return cursor.getString(0);
        } else {
            return "";
        }
    }


}
