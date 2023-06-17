package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.sanauno.database.AssessManager;
import com.example.sanauno.database.DBHelper;

import java.util.List;

public class StudentViewAssessment extends AppCompatActivity {

    ListView lvStudentAssessment;
    SimpleCursorAdapter scAdapter;
    AssessManager aManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_assessment);

        lvStudentAssessment = findViewById(R.id.lvStudentAssessment);

        Bundle bundle = getIntent().getExtras();
        int insID = bundle.getInt("insID");
        int lrn = bundle.getInt("lrn");
        Toast.makeText(this, lrn + " | " + insID, Toast.LENGTH_SHORT).show();

        aManager =  new AssessManager(this);
        aManager.openDB();
        Cursor cursor = aManager.fecthStuAssessment(insID, lrn);

        String[] from = new String[]{DBHelper.CLM_ASSESSTITLE, DBHelper.CLM_ASSESSLRN, DBHelper.CLM_ASSESSSCORE};
        int[] to = new int[]{R.id.txtWorkName, R.id.txtStudentName, R.id.txtStudentScore};
        scAdapter =  new SimpleCursorAdapter(this,R.layout.worklist, cursor, from, to, 0);
        lvStudentAssessment.setAdapter(scAdapter);

    }
}