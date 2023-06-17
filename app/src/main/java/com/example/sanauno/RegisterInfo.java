package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterInfo extends AppCompatActivity {


    TextInputLayout txtFnameLayout, txtLnameLayout, txtLrnIDLayout;
    EditText txtEditFname, txtEditLname, txtEditLrnID;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);

        txtFnameLayout = findViewById(R.id.txtFnameLayout);
        txtLnameLayout = findViewById(R.id.txtLnameLayout);
        txtLrnIDLayout = findViewById(R.id.txtLrnIDLayout);
        txtEditFname = txtFnameLayout.getEditText();
        txtEditLname = txtLnameLayout.getEditText();
        txtEditLrnID = txtLrnIDLayout.getEditText();
        btnNext = findViewById(R.id.btnNext);

        int red = ContextCompat.getColor(this, R.color.red);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtEditFname.getText().toString().equals("") || txtEditLname.getText().toString().equals("") || txtEditLrnID.getText().toString().equals("")){
                    if(txtEditFname.getText().toString().equals("")){
                        txtFnameLayout.setHelperText("*Required");
                        txtFnameLayout.setHelperTextColor(ColorStateList.valueOf(red));
                    } else {
                        txtFnameLayout.setHelperText(null);
                    }
                    if(txtEditLname.getText().toString().equals("")){
                        txtLnameLayout.setHelperText("*Required");
                        txtLnameLayout.setHelperTextColor(ColorStateList.valueOf(red));
                    } else {
                        txtLnameLayout.setHelperText(null);
                    }
                    if (txtEditLrnID.getText().toString().equals("")){
                        txtLrnIDLayout.setHelperText("*Required");
                        txtLrnIDLayout.setHelperTextColor(ColorStateList.valueOf(red));
                    } else {
                        txtLrnIDLayout.setHelperText(null);
                    }
                } else {
                    String fName = txtEditFname.getText().toString();
                    String lName = txtEditLname.getText().toString();
                    int Lrn = Integer.parseInt(txtEditLrnID.getText().toString());

                    openNext(fName, lName, Lrn);
                }

            }
        });
    }

    void openNext(String fName, String lName, int lrn){
        Intent intent = new Intent(this, Register.class);
        Bundle bundle = new Bundle();
        bundle.putString("fName", fName);
        bundle.putString("lName", lName);
        bundle.putInt("lrnID", lrn);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}