package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanauno.database.ClassManager;
import com.example.sanauno.database.DBHelper;
import com.example.sanauno.database.InstructorManager;

import org.w3c.dom.Text;

import java.util.List;

public class StudentAddClass extends AppCompatActivity {

    Button btnEnrollNow, btnCancelEnroll;
    TextView txtStudName, txtCName;
    Spinner spnCCode, spnCName;
    ClassManager cManager;

    String cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_class);

        btnCancelEnroll = (Button) findViewById(R.id.btnCancelEnroll);
        btnEnrollNow = (Button) findViewById(R.id.btnEnrollNow);
        txtCName = (TextView) findViewById(R.id.txtCName);
        txtStudName = (TextView) findViewById(R.id.txtStudName);
        spnCCode = (Spinner) findViewById(R.id.spnCCode);
        spnCName = (Spinner) findViewById(R.id.spnCName);

        Bundle bundle = getIntent().getExtras();
        int lrn = bundle.getInt("lrn");

        InstructorManager insManager = new InstructorManager(this);
        cManager =  new ClassManager(this);

        spinnerData();

        btnEnrollNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cc = spnCCode.getSelectedItem().toString();
                String ins = spnCName.getSelectedItem().toString();
                int id = insManager.fecthClassID(cc, ins);
                System.out.println(id + ", " + cc + ", " + ins + ", " + lrn);

                cManager.openDB();
                cManager.insertClass(lrn,cName,cc,id,ins, "off",0);
                Toast.makeText(StudentAddClass.this, "Class Enrolled", Toast.LENGTH_SHORT).show();

            }
        });
        btnCancelEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public void spinnerData(){

        InstructorManager insManager = new InstructorManager(this);

        spnCCode = (Spinner) findViewById(R.id.spnCCode);
        List<String> insClass = insManager.getAllClass();
        ArrayAdapter<String> dataClass = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, insClass);
        dataClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCCode.setAdapter(dataClass);
        System.out.println(insClass);

        spnCCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cc = spnCCode.getSelectedItem().toString();
                cName = insManager.getClassName(cc);
                txtCName.setText(cName);
                updateIns(cc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void updateIns(String cc){
        InstructorManager insManager = new InstructorManager(this);
        spnCName = (Spinner) findViewById(R.id.spnCName);
        List<String> instructor = insManager.getInstructor(cc);
        ArrayAdapter<String> dataInstructor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, instructor);
        dataInstructor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCName.setAdapter(dataInstructor);
        System.out.println(instructor);
    }
}