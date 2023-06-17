package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanauno.database.UserManager;
import com.example.sanauno.sng.UserInfo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    Button btnRegister;
    TextInputLayout txtEmailLayout, txtPassLayout;
    EditText txtEditEmail, txtEditPass;
    CheckBox cbRU;

    boolean isUNPAcc = false;
    boolean isInfoPresent = false;
    List<String> info =  new ArrayList<>();
    String passRating = "";

    private UserInfo uInfo;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        txtEmailLayout = findViewById(R.id.txtEmailLayout);
        txtPassLayout = findViewById(R.id.txtPassLayout);
        txtEditEmail = txtEmailLayout.getEditText();
        txtEditPass = txtPassLayout.getEditText();
        //etxtEmailAcc = (EditText) findViewById(R.id.etxtEmailAcc);
        //etxtFname = (EditText) findViewById(R.id.etxtFname);
        //etxtLname = (EditText) findViewById(R.id.etxtLname);
        //etxtPasswordAcc = (EditText) findViewById(R.id.etxtPasswordAcc);
        //etxtLrnid = (EditText) findViewById(R.id.etxtLrnid);
        cbRU = (CheckBox) findViewById(R.id.cbRU);

        uInfo = new UserInfo();
        userManager = new UserManager(this);

        Bundle bundle = getIntent().getExtras();
        String fName = bundle.getString("fName");
        String lName = bundle.getString("lName");
        int lrnID = bundle.getInt("lrnID");

        info = getallInfo(fName,lName, lrnID);

        for (String str : info){
            System.out.println(str);
        }

        int red = ContextCompat.getColor(this, R.color.red);
        int yellow = ContextCompat.getColor(this, R.color.yellow);
        int orange = ContextCompat.getColor(this, R.color.orange);
        int green = ContextCompat.getColor(this, R.color.green);

        TextWatcher txtwatch = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass = txtEditPass.getText().toString();
                passRating = computePassStrenght(pass);
                if(passRating.equals("Weak")){
                    txtPassLayout.setHelperText("*Password is Weak. Please try adding character combination");
                    txtPassLayout.setHelperTextColor(ColorStateList.valueOf(orange));
                } else if (passRating.equals("Good")){
                    txtPassLayout.setHelperText("*Password is Good.");
                    txtPassLayout.setHelperTextColor(ColorStateList.valueOf(yellow));
                } else if (passRating.equals("Strong")) {
                    txtPassLayout.setHelperText("*Password is Strong");
                    txtPassLayout.setHelperTextColor(ColorStateList.valueOf(green));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        txtEditPass.addTextChangedListener(txtwatch);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtEditEmail != null || txtPassLayout  != null){
                    String email = txtEditEmail.getText().toString();
                    String pass = txtEditPass.getText().toString();
                    if (email.trim().isEmpty()){
                        txtEmailLayout.setHelperText("*Required");
                        txtEmailLayout.setHelperTextColor(ColorStateList.valueOf(red));
                    } else {
                        if(email.contains(".ccit@unp.edu.ph")){
                            txtEmailLayout.setHelperText(null);
                            isUNPAcc = true;
                        } else {
                            isUNPAcc = false;
                            txtEmailLayout.setHelperText("*Email Should be a UNP Account");
                            txtEmailLayout.setHelperTextColor(ColorStateList.valueOf(red));
                        }
                    }
                    if (pass.trim().isEmpty()){
                        txtPassLayout.setHelperText("*Required");
                        txtPassLayout.setHelperTextColor(ColorStateList.valueOf(red));
                    } else {
                        info = getallInfo(fName, lName, lrnID);
                        for (String str : info){
                            System.out.println(str);
                        }
                        isInfoPresent = checkRepitition(info,pass);
                        System.out.println(isInfoPresent);
                        if(isInfoPresent){
                            txtPassLayout.setHelperText("*Do not use your personal info in creating password.");
                            txtPassLayout.setHelperTextColor(ColorStateList.valueOf(red));
                        } else {
                            txtPassLayout.setHelperText(null);
                        }
                    }
                }


                if (txtEditEmail.getText().toString().matches("") || txtEditPass.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill up all requirements.", Toast.LENGTH_SHORT).show();
                } else {

                    if(cbRU.isChecked()){
                        uInfo.setRole("Student");
                        uInfo.setLrn(lrnID);
                        uInfo.setIdnum(0);
                    } else {
                        uInfo.setRole("Teacher");
                        uInfo.setIdnum(lrnID);
                        uInfo.setLrn(0);
                    }

                    if((passRating == "Good" || passRating == "Strong") && isInfoPresent == false && isUNPAcc){
                        Toast.makeText(Register.this, "Valid Account", Toast.LENGTH_SHORT).show();
                        uInfo.setName(fName + " " + lName);
                        uInfo.setEmail(txtEditEmail.getText().toString());
                        uInfo.setPassword(txtEditPass.getText().toString());

                        userManager.openDB();

                        boolean isUsed = userManager.isEmailAlreadyUsed(uInfo.getEmail());

                        if(isUsed == false){
                            userManager.insert_User(uInfo.getName(), uInfo.getEmail(), uInfo.getPassword(), uInfo.getRole(), uInfo.getLrn(), uInfo.getIdnum());
                            String str = "Name: " + uInfo.getName() + ", Email: " + uInfo.getEmail() + " Pass: " + uInfo.getPassword() + " Role: " + uInfo.getRole() + " lrn : " + uInfo.getLrn() + " id: " + uInfo.getIdnum();
                            Toast.makeText(getApplicationContext(), "User Successfully Registered.", Toast.LENGTH_SHORT).show();
                            back2Choose();
                        } else {
                            txtEmailLayout.setHelperText("*Email is already used.");
                            txtEmailLayout.setHelperTextColor(ColorStateList.valueOf(red));
                        }

                    }


                }
            }
        });


    }

    List getallInfo(String fName, String lName, int lrnID){
        List<String> myInfo = new ArrayList();
        myInfo.add(fName);
        myInfo.add(lName);
        myInfo.add(String.valueOf(lrnID));
        return myInfo;
    }

    boolean checkRepitition(List<String> info, String pass){
        boolean result = false;

        while(!info.isEmpty()){
            String search = info.get(0);
            for(int i = 0; i <= pass.length() - search.length(); i++){
                if(pass.substring(i, i+search.length()).equalsIgnoreCase(search)){
                    result = true;
                    break;
                }
            }
            info.remove(0);
        }
        return result;
    }


    String computePassStrenght(String pass){
        int strength = 0;
        String rating = "";

        if(pass.matches(".*[A-Z].*")){
            strength += 2;
        }
        if(pass.matches(".*\\d.*")){
            strength += 2;
        }
        if(pass.length() > 11){
            strength+=3;
        }
        if(pass.matches(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*")){
            strength+=3;
        }

        if(strength <= 4){
            rating = "Weak";
        } else if(strength <= 7){
            rating = "Good";
        } else if(strength <= 10){
            rating = "Strong";
        }

        return rating;
    }

    public void back2Choose(){
        Intent intent = new Intent(this, Choose.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        finish();
    }
}