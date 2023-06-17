package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.sanauno.database.DBHelper;
import com.example.sanauno.database.ExamManager;
import com.example.sanauno.database.QuizManager;

public class StudentViewExam extends AppCompatActivity {

    ListView lvStudentExam;
    SimpleCursorAdapter scAdapter;
    ExamManager eManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_exam);

        lvStudentExam = findViewById(R.id.lvStudentExam);

        Bundle bundle = getIntent().getExtras();
        int insID = bundle.getInt("insID");
        int lrn = bundle.getInt("lrn");
        Toast.makeText(this, lrn + " | " + insID, Toast.LENGTH_SHORT).show();

        eManager = new ExamManager(this);
        eManager.openDB();
        Cursor cursor = eManager.fecthStuExam(insID, lrn);

        String[] from = new String[]{DBHelper.CLM_EXAMTITLE, DBHelper.CLM_EXAMLRN, DBHelper.CLM_EXAMSCORE};
        int[] to = new int[]{R.id.txtWorkName, R.id.txtStudentName, R.id.txtStudentScore};
        scAdapter =  new SimpleCursorAdapter(this,R.layout.worklist, cursor, from, to, 0);
        lvStudentExam.setAdapter(scAdapter);



    }
}