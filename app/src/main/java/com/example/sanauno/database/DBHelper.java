package com.example.sanauno.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public Context context;

    public static final String DB_NAME = "dbUno";
    public static final int DB_VERSION = 1;

    // table for users
    public static final String TBL_USER = "table_user";
    public static final String CLM_ID = "_id";
    public static final String CLM_NAME = "name";
    public static final String CLM_EMAIL = "email";
    public static final String CLM_PASSWORD = "password";
    public static final String CLM_ROLE = "role";
    public static final String CLM_LRN = "lrn";
    public static final String CLM_IDNUM = "id_num";

    // table for Subject Instructor
    public static final String TBL_INSTRUCTOR = "table_instructors";
    public static final String CLM_INID = "_id";
    public static final String CLM_CLASSCODE = "classcode";
    public static final String CLM_ICNAME = "ins_classname";
    public static final String CLM_ISSEMESTER = "semester";
    public static final String CLM_INSTRUCTOR = "instructor";

    //table for student's classes
    public static final String TBL_CLASS = "table_class";
    public static final String CLM_CLRN = "studnt_lrn";
    public static final String CLM_CNAME = "class_name";
    public static final String CLM_CCODE = "classcode";
    public static final String CLM_CID = "_id";
    public static final String CLM_CLINSTRUCTOR = "class_instructorID";
    public static final String CLM_CINSNAME = "class_instructorName";
    public static final String CLM_FINALGRADE = "final_grade";
    public static final String CLM_SEMESTER = "semester";
    public static final String CLM_CSHOW = "show_grade";

    public static final String TBL_QUIZ = "table_quiz";
    public static final String CLM_QUIZID = "_id";
    public static final String CLM_QUIZTITLE = "quiz_title";
    public static final String CLM_QUIZINSTRUCTOR = "class_instructor";
    public static final String CLM_QUIZLRN = "lrn";
    public static final String CLM_QUIZITEM = "item";
    public static final String CLM_QUIZSCORE = "score";
    public static final String CLM_QUIZSHOW = "show";

    public static final String TBL_ASSESS = "table_assess";
    public static final String CLM_ASSESSID = "_id";
    public static final String CLM_ASSESSTITLE = "quiz_title";
    public static final String CLM_ASSESSINSTRUCTOR = "class_instructor";
    public static final String CLM_ASSESSLRN = "lrn";
    public static final String CLM_ASSESSITEM = "item";
    public static final String CLM_ASSESSSCORE = "score";
    public static final String CLM_ASSESSSHOW = "show";

    public static final String TBL_EXAM = "table_exam";
    public static final String CLM_EXAMID = "_id";
    public static final String CLM_EXAMTITLE = "quiz_title";
    public static final String CLM_EXAMINSTRUCTOR = "class_instructor";
    public static final String CLM_EXAMLRN = "lrn";
    public static final String CLM_EXAMITEM = "item";
    public static final String CLM_EXAMSCORE = "score";
    public static final String CLM_EXAMSHOW = "show";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuery_User = "CREATE TABLE " + TBL_USER + " ( " +
                CLM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLM_NAME + " TEXT, " +
                CLM_EMAIL + " TEXT, " +
                CLM_PASSWORD + " TEXT, " +
                CLM_LRN + " INTEGER," +
                CLM_IDNUM + " INTEGER," +
                CLM_ROLE + " TEXT);";
        sqLiteDatabase.execSQL(createQuery_User);

        String createQuery_Class = "CREATE TABLE " + TBL_CLASS + " ( " +
                CLM_CID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLM_CCODE + " TEXT, " +
                CLM_CLINSTRUCTOR + " INTERGER, "+
                CLM_CINSNAME + " TEXT, " +
                CLM_CLRN + " TEXT," +
                CLM_CNAME + " TEXT, " +
                CLM_FINALGRADE + " DOUBLE, " +
                CLM_SEMESTER + " TEXT, " +
                CLM_CSHOW + " TEXT);";
        sqLiteDatabase.execSQL(createQuery_Class);

        String createQuery_Instructors = "CREATE TABLE " + TBL_INSTRUCTOR + " ( " +
                CLM_INID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLM_CLASSCODE + " TEXT, " +
                CLM_ICNAME + " TEXT, " +
                CLM_ISSEMESTER + " TEXT, " +
                CLM_INSTRUCTOR + " TEXT);";
        sqLiteDatabase.execSQL(createQuery_Instructors);

        String createQuery_Quiz = "CREATE TABLE " + TBL_QUIZ + " ( " +
                CLM_QUIZID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLM_QUIZTITLE + " TEXT," +
                CLM_QUIZINSTRUCTOR + " INTEGER, " +
                CLM_QUIZLRN + " INTEGER, " +
                CLM_QUIZITEM + " INTEGER, " +
                CLM_QUIZSCORE + " INTEGER, " +
                CLM_QUIZSHOW + " TEXT); ";
        sqLiteDatabase.execSQL(createQuery_Quiz);

        String createQuery_Assess = "CREATE TABLE " + TBL_ASSESS + " ( " +
                CLM_ASSESSID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLM_ASSESSTITLE + " TEXT," +
                CLM_ASSESSINSTRUCTOR + " INTEGER, " +
                CLM_ASSESSLRN + " INTEGER, " +
                CLM_ASSESSITEM + " INTEGER, " +
                CLM_ASSESSSCORE + " INTEGER, " +
                CLM_ASSESSSHOW + " TEXT); ";
        sqLiteDatabase.execSQL(createQuery_Assess);

        String createQuery_Exam = "CREATE TABLE " + TBL_EXAM + " ( " +
                CLM_EXAMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLM_EXAMTITLE + " TEXT," +
                CLM_EXAMINSTRUCTOR + " INTEGER, " +
                CLM_EXAMLRN + " INTEGER, " +
                CLM_EXAMITEM + " INTEGER, " +
                CLM_EXAMSCORE + " INTEGER, " +
                CLM_EXAMSHOW + " TEXT); ";
        sqLiteDatabase.execSQL(createQuery_Exam);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_CLASS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_QUIZ);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_ASSESS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_EXAM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_INSTRUCTOR);
        onCreate(sqLiteDatabase);
    }
}
