package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.sanauno.database.DBHelper;
import com.example.sanauno.database.UserManager;
import com.example.sanauno.sng.UserInfo;

public class TeacherViewStudent extends AppCompatActivity {

    ListView lv;
    SimpleCursorAdapter scAdapter;

    TextView txtEmptyStudent;

    UserManager uManager;
    UserInfo uInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_student);

        uInfo = new UserInfo();
        uManager = new UserManager(this);
        uManager.openDB();

        lv = (ListView) findViewById(R.id.lvStudentList);
        txtEmptyStudent = (TextView) findViewById(R.id.txtEmptyStudent);

        Cursor cursor = uManager.fetch_StudentUser();

        String[] from = new String[]{DBHelper.CLM_ID, DBHelper.CLM_NAME, DBHelper.CLM_LRN, DBHelper.CLM_ROLE};
        int[] to = new int[]{R.id.txtEmptyStudent, R.id.txtStudentName, R.id.txtLRN, R.id.txtRole};

        scAdapter = new SimpleCursorAdapter(this, R.layout.studentview, cursor, from, to, 0);

        lv.setEmptyView(txtEmptyStudent);
        lv.setAdapter(scAdapter);

    }
}