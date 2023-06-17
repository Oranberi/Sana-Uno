package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sanauno.database.AssessManager;
import com.example.sanauno.database.ExamManager;
import com.example.sanauno.database.QuizManager;

public class EditWork extends AppCompatActivity {

    TextView txtEditWorkName, edtxtStudName, txtWorkItem;
    Button btnUpdateWork, btnCancelUpdate;
    EditText edtxtScore;
    AssessManager aManager;
    QuizManager qManager;
    ExamManager eManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work);

        txtEditWorkName = findViewById(R.id.editWorkName);
        edtxtStudName = findViewById(R.id.edtxtStudName);
        txtWorkItem = findViewById(R.id.txtWorkItem);
        btnUpdateWork = findViewById(R.id.btnUpdateWork);
        btnCancelUpdate = findViewById(R.id.btnCancelUpdate);
        edtxtScore = findViewById(R.id.edtxtScore);

        aManager = new AssessManager(this);
        aManager.openDB();

        qManager = new QuizManager(this);
        qManager.openDB();

        eManager = new ExamManager(this);
        eManager.openDB();

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("ID");
        String workName = bundle.getString("Title");
        int lrn = Integer.parseInt(bundle.getString("Lrn"));
        int score = Integer.parseInt(bundle.getString("Score"));
        int item = Integer.parseInt(bundle.getString("Item"));
        int worktype = bundle.getInt("worktype");

        txtEditWorkName.setText(workName);
        edtxtStudName.setText(lrn+"");
        txtWorkItem.setText(item+"");
        edtxtScore.setText(score+"");

        edtxtScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int value = Integer.parseInt(editable.toString());
                    if(value > item){
                        edtxtScore.setText(""+item);
                    }
                } catch (NumberFormatException e){

                }
            }
        });

        btnUpdateWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updatedScore = Integer.parseInt(edtxtScore.getText().toString());
                switch (worktype){
                    case 1:
                        aManager.updateWork(id, updatedScore, "on");
                        finish();
                        break;
                    case 2:
                        qManager.updateWork(id, updatedScore, "on");
                        finish();
                        break;
                    case 3:
                        eManager.updateWork(id, updatedScore, "on");
                        finish();
                        break;
                }

            }
        });

    }
}