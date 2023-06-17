package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sanauno.database.InstructorManager;
import com.example.sanauno.sng.InClassInfo;
import com.example.sanauno.sng.UserInfo;
import com.google.android.material.textfield.TextInputLayout;

public class AddInstructorClass extends AppCompatActivity {

    TextInputLayout txtEnterClassCode, txtEnterClassName;
    EditText txtEnterCName, txtEnterCCode;
    Button btnCreateClass, btnCancelClass;

    UserInfo uInfo;
    InClassInfo icInfo;
    InstructorManager instructorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor_class);

        txtEnterClassCode =findViewById(R.id.txtEnterClassCode);
        txtEnterClassName = findViewById(R.id.txtEnterClassName);
        txtEnterCCode = txtEnterClassCode.getEditText();
        txtEnterCName = txtEnterClassName.getEditText();

        btnCreateClass = (Button) findViewById(R.id.btnCreateClass);
        btnCancelClass = (Button) findViewById(R.id.btnCancelClass);

        uInfo = new UserInfo();
        icInfo = new InClassInfo();
        instructorManager = new InstructorManager(this);

        Bundle bundle = getIntent().getExtras();
        uInfo.setName(bundle.getString("Name"));

        btnCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtEnterCCode.getText().toString().matches("") && txtEnterCName.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill all field.", Toast.LENGTH_SHORT).show();
                } else {
                    instructorManager.openDB();
                    icInfo.setInstructor(uInfo.getName());
                    icInfo.setClasscode(txtEnterCCode.getText().toString());
                    icInfo.setClassname(txtEnterCName.getText().toString());

                    instructorManager.insertInstructorClass(icInfo.getClasscode(), icInfo.getInstructor(), icInfo.getClassname(), icInfo.getSemester());
                    Toast.makeText(getApplicationContext(), "Class has been created.", Toast.LENGTH_SHORT).show();

                    txtEnterCCode.setText(null);
                    txtEnterCName.setText(null);
                    finish();
                }
            }
        });

        btnCancelClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}