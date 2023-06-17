package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.sanauno.database.DBHelper;
import com.example.sanauno.database.ExamManager;
import com.example.sanauno.database.QuizManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TeacherViewExam extends AppCompatActivity {

    ListView lvExam;
    SimpleCursorAdapter scAdapter;
    FloatingActionButton btnAddExam;

    ExamManager eManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_exam);

        lvExam = findViewById(R.id.lvExam);
        btnAddExam = findViewById(R.id.btnAddExam);

        Bundle bundle = getIntent().getExtras();
        int ins_id = bundle.getInt("ins_ID");
        int id = ins_id;
        Toast.makeText(this, ""+ins_id, Toast.LENGTH_SHORT).show();

        eManager = new ExamManager(this);
        eManager.openDB();
        Cursor cursor = eManager.fecthAllExam(ins_id);

        String[] from = new String[]{DBHelper.CLM_EXAMTITLE, DBHelper.CLM_EXAMLRN, DBHelper.CLM_EXAMSCORE};
        int[] to = new int[]{R.id.txtWorkName, R.id.txtStudentName, R.id.txtStudentScore};
        scAdapter =  new SimpleCursorAdapter(this,R.layout.worklist, cursor, from, to, 0);
        lvExam.setAdapter(scAdapter);

        lvExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TeacherViewExam.this, EditWork.class);
                Bundle bundle1 = new Bundle();
                Cursor cursor1 = (Cursor) scAdapter.getItem(i);

                String examID = cursor1.getString(0);
                String examTitle = cursor1.getString(1);
                String examLrn = cursor1.getString(3);
                String examScore = cursor1.getString(5);
                String examItem = cursor1.getString(4);

                bundle1.putInt("worktype", 3);
                bundle1.putString("ID", examID);
                bundle1.putString("Title", examTitle);
                bundle1.putString("Lrn", examLrn);
                bundle1.putString("Score", examScore);
                bundle1.putString("Item", examItem);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btnAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddQuiz(id);
            }
        });
    }

    void openAddQuiz(int insID){
        Intent intent = new Intent(this, AddWork.class);
        Bundle bundle = new Bundle();
        bundle.putInt("insID", insID);
        bundle.putInt("work", 3);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}