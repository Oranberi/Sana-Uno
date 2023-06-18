package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.sanauno.database.AssessManager;
import com.example.sanauno.database.DBHelper;
import com.example.sanauno.database.QuizManager;

public class StudentViewQuiz extends AppCompatActivity {

    ListView lvStudentQuiz;
    SimpleCursorAdapter scAdapter;
    QuizManager qManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_quiz);

        lvStudentQuiz = findViewById(R.id.lvStudentQuiz);

        Bundle bundle = getIntent().getExtras();
        int insID = bundle.getInt("insID");
        int lrn = bundle.getInt("lrn");

        qManager =  new QuizManager(this);
        qManager.openDB();
        Cursor cursor = qManager.fecthStuQuiz(insID, lrn);

        String[] from = new String[]{DBHelper.CLM_QUIZTITLE, DBHelper.CLM_QUIZLRN, DBHelper.CLM_QUIZSCORE};
        int[] to = new int[]{R.id.txtWorkName, R.id.txtStudentName, R.id.txtStudentScore};
        scAdapter =  new SimpleCursorAdapter(this,R.layout.worklist, cursor, from, to, 0);
        lvStudentQuiz.setAdapter(scAdapter);


    }
}