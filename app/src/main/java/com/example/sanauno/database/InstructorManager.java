package com.example.sanauno.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class InstructorManager {

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public InstructorManager(Context c){
        this.context = c;
    }

    public InstructorManager openDB() throws SQLException {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }
    public void closeDB(){
        dbHelper.close();
    }

    public void insertInstructorClass(String classcode, String instructor, String classname, String semester){
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.CLM_CLASSCODE, classcode);
        cv.put(dbHelper.CLM_INSTRUCTOR, instructor);
        cv.put(dbHelper.CLM_ICNAME, classname);
        cv.put(dbHelper.CLM_SEMESTER, semester);

        long result = sqLiteDatabase.insert(dbHelper.TBL_INSTRUCTOR, null, cv);
    }

    public List<String> getAllClass(){
        List<String> insClass = new ArrayList<>();
        DBHelper mydbHelper =  new DBHelper(context.getApplicationContext());
        sqLiteDatabase = mydbHelper.getReadableDatabase();
        String[] clm = new String[]{dbHelper.CLM_INID, dbHelper.CLM_CLASSCODE};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_INSTRUCTOR, clm, null, null,null,null,null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + dbHelper.CLM_CLASSCODE + " FROM " + dbHelper.TBL_INSTRUCTOR, null);
        //int count = cursor.getCount();
        if(cursor.moveToFirst()){
            do{
                insClass.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return insClass;
    }

    public List<String> getInstructor(String cc){
        List<String> insClass = new ArrayList<>();
        DBHelper mydbHelper =  new DBHelper(context.getApplicationContext());
        sqLiteDatabase = mydbHelper.getReadableDatabase();
        String[] clm = new String[]{dbHelper.CLM_INID, dbHelper.CLM_INSTRUCTOR};
        //Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_INSTRUCTOR, clm, null, null,null,null,null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + dbHelper.CLM_INSTRUCTOR + " FROM " + dbHelper.TBL_INSTRUCTOR + " WHERE " + dbHelper.CLM_CLASSCODE + "= '" + cc + "'", null);
        //int count = cursor.getCount();
        if(cursor.moveToFirst()){
            do{
                insClass.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return insClass;
    }

    public void delete_User(String row_id){
        SQLiteDatabase sDB = dbHelper.getWritableDatabase();
        dbHelper.getWritableDatabase();
        sqLiteDatabase.delete(dbHelper.TBL_INSTRUCTOR, dbHelper.CLM_INID + "=?", new String[]{row_id});
    }
    public String getClassName(String cc){
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + dbHelper.CLM_ICNAME + " FROM " + dbHelper.TBL_INSTRUCTOR + " WHERE " + dbHelper.CLM_CLASSCODE + "='" + cc + "'" , null);
        if(cursor != null && cursor.moveToFirst()){
            return cursor.getString(0);
        } else {
            return "";
        }
    }
    public int fecthClassID(String cc, String ins){
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + dbHelper.CLM_INID + " FROM " + dbHelper.TBL_INSTRUCTOR + " WHERE " + dbHelper.CLM_CLASSCODE + "='" + cc + "' AND " + dbHelper.CLM_INSTRUCTOR
                + "='" + ins + "'", null);
        if(cursor != null && cursor.moveToFirst()){
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    public Cursor fetch_InstrucorClass(String Name){
        String[] clm = new String[]{dbHelper.CLM_INID, dbHelper.CLM_CLASSCODE, dbHelper.CLM_ICNAME, dbHelper.CLM_ISSEMESTER, dbHelper.CLM_INSTRUCTOR};
        String[] selectionArgs = new String[]{Name};
        Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_INSTRUCTOR, clm, dbHelper.CLM_INSTRUCTOR +" =?", selectionArgs,null,null,null);
        //Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + dbHelper.TBL_INSTRUCTOR + " WHERE " + dbHelper.CLM_INSTRUCTOR + "=" + android.database.DatabaseUtils.sqlEscapeString(Name), null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

}
