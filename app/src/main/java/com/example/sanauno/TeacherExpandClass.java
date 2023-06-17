package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sanauno.sng.InClassInfo;

public class TeacherExpandClass extends AppCompatActivity {

    TextView txtIClassName, txtICCode, txtInstructorName;
    ImageButton btnTeacherQuiz, btnTeacherAssessment, btnTeacherExam, btnTeacherEnrolled;
    InClassInfo insInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_expand_class);

        txtICCode = (TextView) findViewById(R.id.txtICCode);
        txtIClassName = (TextView) findViewById(R.id.txtIClassName);
        txtInstructorName = (TextView) findViewById(R.id.txtInstructorName);
        btnTeacherAssessment = (ImageButton) findViewById(R.id.btnTeacherAssessment);
        btnTeacherQuiz = (ImageButton) findViewById(R.id.btnTeacherQuiz);
        btnTeacherExam = (ImageButton) findViewById(R.id.btnTeacherExam);
        btnTeacherEnrolled = (ImageButton) findViewById(R.id.btnTeacherEnrolled);

        insInfo = new InClassInfo();

        Bundle bundle = getIntent().getExtras();
        insInfo.setId(bundle.getString("ic_ID"));
        insInfo.setClasscode(bundle.getString("icCode"));
        insInfo.setClassname(bundle.getString("icName"));
        insInfo.setInstructor(bundle.getString("cInstructor"));

        txtICCode.setText(insInfo.getClasscode());
        txtIClassName.setText(insInfo.getClassname());
        txtInstructorName.setText(insInfo.getInstructor());

        btnTeacherAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAssess(Integer.parseInt(insInfo.getId()));
            }
        });

        btnTeacherQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuiz(Integer.parseInt(insInfo.getId()));
            }
        });

        btnTeacherExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExam(Integer.parseInt(insInfo.getId()));
            }
        });

        btnTeacherEnrolled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEnrolled(Integer.parseInt(insInfo.getId()));
            }
        });

    }

    public void openAssess(int ic_id){
        Intent intent = new Intent(this, TeacherViewAssessment.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ins_ID", ic_id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openQuiz(int ic_id){
        Intent intent = new Intent(this, TeacherViewQuiz.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ins_ID", ic_id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openExam(int ic_id){
        Intent intent = new Intent(this, TeacherViewExam.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ins_ID", ic_id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openEnrolled(int ic_id){
        Intent intent = new Intent(this, TeacherViewEnrolledStudent.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ins_ID", ic_id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}