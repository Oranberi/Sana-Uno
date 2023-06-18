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
import com.example.sanauno.database.QuizManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TeacherViewQuiz extends AppCompatActivity {

    ListView lvQuizz;
    SimpleCursorAdapter scAdapter;
    FloatingActionButton btnAddQuiz;

    QuizManager qManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_quiz);

        lvQuizz = findViewById(R.id.lvQuiz);
        btnAddQuiz = findViewById(R.id.btnAddQuiz);

        Bundle bundle = getIntent().getExtras();
        int ins_id = bundle.getInt("ins_ID");
        int id = ins_id;

        qManager = new QuizManager(this);
        qManager.openDB();
        Cursor cursor = qManager.fecthAllQuiz(ins_id);

        String[] from = new String[]{DBHelper.CLM_QUIZTITLE, DBHelper.CLM_QUIZLRN, DBHelper.CLM_QUIZSCORE};
        int[] to = new int[]{R.id.txtWorkName, R.id.txtStudentName, R.id.txtStudentScore};
        scAdapter =  new SimpleCursorAdapter(this,R.layout.worklist, cursor, from, to, 0);
        lvQuizz.setAdapter(scAdapter);

        lvQuizz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TeacherViewQuiz.this, EditWork.class);
                Bundle bundle1 = new Bundle();
                Cursor cursor1 = (Cursor) scAdapter.getItem(i);

                String quizID = cursor1.getString(0);
                String quizTitle = cursor1.getString(1);
                String quizLrn = cursor1.getString(3);
                String quizScore = cursor1.getString(5);
                String quizItem = cursor1.getString(4);

                bundle1.putInt("worktype", 2);
                bundle1.putString("ID", quizID);
                bundle1.putString("Title", quizTitle);
                bundle1.putString("Lrn", quizLrn);
                bundle1.putString("Score", quizScore);
                bundle1.putString("Item", quizItem);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btnAddQuiz.setOnClickListener(new View.OnClickListener() {
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
        bundle.putInt("work", 2);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}