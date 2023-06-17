package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StudentExpandClass extends AppCompatActivity {

    TextView txtCNameExpand, txtINameExpand, txtShowGrade;
    ImageButton btnOpenAssess, btnOpenQuizzes, btnOpenExams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_expand_class);

        txtCNameExpand = findViewById(R.id.txtCNameExpand);
        txtINameExpand = findViewById(R.id.txtINameExpand);
        txtShowGrade = findViewById(R.id.txtShowGrade);
        btnOpenAssess = findViewById(R.id.btnOpenAssess);
        btnOpenQuizzes = findViewById(R.id.btnOpenQuiz);
        btnOpenExams = findViewById(R.id.btnOpenExams);

        Bundle bundle = getIntent().getExtras();
        String cID = bundle.getString("cID");
        String cCode = bundle.getString("cCode");
        String cName = bundle.getString("cName");
        String cIns = bundle.getString("cIns");
        String finalGrade = bundle.getString("fGrade");
        int lrn = bundle.getInt("lrn");

        txtINameExpand.setText(cIns);
        txtCNameExpand.setText(cName);
        txtShowGrade.setText(finalGrade);

        btnOpenAssess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentAssess(Integer.parseInt(cID), lrn);
            }
        });

        btnOpenQuizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentQuiz(Integer.parseInt(cID), lrn);
            }
        });

        btnOpenExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentExam(Integer.parseInt(cID), lrn);
            }
        });

    }
    void openStudentAssess(int insID, int lrn){
        Intent intent = new Intent(this, StudentViewAssessment.class);
        Bundle bundle = new Bundle();
        bundle.putInt("insID", insID);
        bundle.putInt("lrn", lrn);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void openStudentQuiz(int insID, int lrn){
        Intent intent = new Intent(this, StudentViewQuiz.class);
        Bundle bundle = new Bundle();
        bundle.putInt("insID", insID);
        bundle.putInt("lrn", lrn);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void openStudentExam(int insID, int lrn){
        Intent intent = new Intent(this, StudentViewExam.class);
        Bundle bundle = new Bundle();
        bundle.putInt("insID", insID);
        bundle.putInt("lrn", lrn);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}