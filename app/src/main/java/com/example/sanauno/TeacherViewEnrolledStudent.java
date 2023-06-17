package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.sanauno.database.ClassManager;
import com.example.sanauno.database.DBHelper;

public class TeacherViewEnrolledStudent extends AppCompatActivity {

    ListView lvStudentEnrolled;
    SimpleCursorAdapter scAdapter;
    ClassManager cManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_enrolled_student);

        lvStudentEnrolled = findViewById(R.id.lvStudentEnrolled);

        Bundle bundle = getIntent().getExtras();
        int insID = bundle.getInt("ins_ID");

        cManager = new ClassManager(this);
        cManager.openDB();
        Cursor cursor = cManager.fetch_StudentEnrolled(insID);

        String[] from = new String[]{DBHelper.CLM_CLRN, DBHelper.CLM_FINALGRADE};
        int[] to = new int[]{R.id.txtEnrolledLRN, R.id.txtFinalGrade};
        scAdapter = new SimpleCursorAdapter(this, R.layout.enrolledstudentlist, cursor, from, to, 0);
        lvStudentEnrolled.setAdapter(scAdapter);

        lvStudentEnrolled.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TeacherViewEnrolledStudent.this, TeacherComputerGrade.class);
                Bundle bundle1 = new Bundle();
                Cursor cursor1 = (Cursor) scAdapter.getItem(i);

                String studentclassID = cursor1.getString(0);
                String className = cursor1.getString(5);
                String studentLrn = cursor1.getString(4);
                String studentFinalGrade = cursor1.getString(6);

                bundle1.putString("ID", studentclassID);
                bundle1.putString("Name", className);
                bundle1.putString("Lrn", studentLrn);
                bundle1.putString("Final", studentFinalGrade);
                bundle1.putInt("insID", insID);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

    }
}