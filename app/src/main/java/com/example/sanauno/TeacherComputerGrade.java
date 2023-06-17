package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanauno.database.AssessManager;
import com.example.sanauno.database.ClassManager;
import com.example.sanauno.database.ExamManager;
import com.example.sanauno.database.QuizManager;
import com.example.sanauno.database.UserManager;

import java.text.DecimalFormat;
import java.util.List;

public class TeacherComputerGrade extends AppCompatActivity {

    TextView txtComputeStudentName, txtComputedGrade;
    Button btnComputeGrade, btnSaveGrade, btnCancelGrade;
    ImageButton btnOpenCalc;

    UserManager uManager;
    AssessManager aManager;
    QuizManager qManager;
    ExamManager eManager;
    ClassManager cManager;
    DecimalFormat deci;

    double fGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_computer_grade);

        txtComputedGrade = findViewById(R.id.txtComputedGrade);
        txtComputeStudentName = findViewById(R.id.txtComputeStudentName);

        btnComputeGrade = findViewById(R.id.btnComputeGrade);
        btnSaveGrade = findViewById(R.id.btnSaveGrade);
        btnCancelGrade = findViewById(R.id.btnCancelGrade);
        btnOpenCalc = findViewById(R.id.btnOpenCalc);

        uManager = new UserManager(this);
        uManager.openDB();

        aManager = new AssessManager(this);
        aManager.openDB();
        qManager = new QuizManager(this);
        qManager.openDB();
        eManager = new ExamManager(this);
        eManager.openDB();
        cManager = new ClassManager(this);
        cManager.openDB();

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("ID");
        String className = bundle.getString("Name");
        String lrn = bundle.getString("Lrn");
        int insID = bundle.getInt("insID");
        double finalGrade = Double.parseDouble(bundle.getString("Final"));
        String studName = uManager.fecthStudentName(lrn);
        Toast.makeText(this, "id: "+insID, Toast.LENGTH_SHORT).show();

        txtComputeStudentName.setText(studName);
        txtComputedGrade.setText(""+finalGrade);

        btnComputeGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deci =  new DecimalFormat("#.##");

                List<Integer> assessmentScores = aManager.studentAssessmentScores(insID, Integer.parseInt(lrn));
                List<Integer> assessmentItems = aManager.studentAssessmentItems(insID, Integer.parseInt(lrn));

                List<Integer> quizScores = qManager.studentQuizScores(insID, Integer.parseInt(lrn));
                List<Integer> quizItems = qManager.studentQuizItems(insID, Integer.parseInt(lrn));

                List<Integer> examScores = eManager.studentExamScores(insID, Integer.parseInt(lrn));
                List<Integer> examItems = eManager.studentExamItems(insID, Integer.parseInt(lrn));

                int aScoreTotal = getSum(assessmentScores);
                int aItemTotal = getSum(assessmentItems);
                double aPercent = (double) aScoreTotal/aItemTotal;
                aPercent = aPercent * 100;
                double assessmentGrade = aPercent * .4;

                int qScoreTotal = getSum(quizScores);
                int qItemTotal = getSum(quizItems);
                double qPercent = (double) qScoreTotal/qItemTotal;
                qPercent = qPercent * 100;
                double quizGrade = qPercent * .3;

                int eScoreTotal = getSum(examScores);
                int eItemTotal = getSum(examItems);
                double ePercent = (double) eScoreTotal/eItemTotal;
                ePercent = ePercent * 100;
                double examGrade = ePercent * .3;

                String tempGrade = deci.format(assessmentGrade + quizGrade + examGrade);
                fGrade = Double.parseDouble(tempGrade);
                txtComputedGrade.setText(""+fGrade);

            }
        });

        btnSaveGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cManager.updateClass(id, fGrade, "");
                finish();
            }
        });
        btnCancelGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnOpenCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherComputerGrade.this, Calculator.class);
                startActivity(intent);
            }
        });
    }


    public int getSum(List<Integer> value){
        int total = 0;
        for (int i : value){
            total += i;
        }
        return total;
    }

}