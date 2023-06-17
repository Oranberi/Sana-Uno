package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanauno.database.UserManager;
import com.example.sanauno.sng.UserInfo;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    Button btnLogin;
    TextInputLayout txtMyEmailLayout, txtMyPassLayout;
    EditText etxtEmail;
    EditText etxtPassword;

    UserManager uManager;
    UserInfo uInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtMyEmailLayout = findViewById(R.id.txtMyEmailLayout);
        txtMyPassLayout = findViewById(R.id.txtMyPassLayout);

        etxtEmail = txtMyEmailLayout.getEditText();
        etxtPassword = txtMyPassLayout.getEditText();

        uManager = new UserManager(this);
        uManager.openDB();
        uInfo = new UserInfo();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uInfo.setEmail(etxtEmail.getText().toString());
                uInfo.setPassword(etxtPassword.getText().toString());
                Cursor cursor = uManager.fetch_allUser();
                loginCheck(cursor, uInfo.getEmail(), uInfo.getPassword());
            }
        });
    }

    public void loginCheck(Cursor cursor, String emailCheck, String passCheck){
        int red = ContextCompat.getColor(this, R.color.red);
        while(cursor.moveToNext()){
            if(cursor.getString(1).equals(emailCheck)){
                txtMyEmailLayout.setHelperText(null);
                if (cursor.getString(2).equals(passCheck)){
                    txtMyPassLayout.setHelperText(null);
                    if (cursor.getString(3).equals("Teacher")){
                        etxtEmail.setText("");
                        etxtPassword.setText("");
                        open_Tdash(cursor.getString(4));
                        break;
                    } else if(cursor.getString(3).equals("Student")){
                        etxtEmail.setText("");
                        etxtPassword.setText("");
                        open_Sdash(cursor.getString(4), cursor.getInt(5));
                        break;
                    }
                } else {
                    txtMyPassLayout.setHelperText("*Password is Wrong");
                    txtMyPassLayout.setHelperTextColor(ColorStateList.valueOf(red));
                }
            } else {
                txtMyEmailLayout.setHelperText("*Email is Wrong");
                txtMyEmailLayout.setHelperTextColor(ColorStateList.valueOf(red));
            }
        }
    }

    public void open_Tdash(String Name){
        Intent intent = new Intent(this, TeacherDash.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name", Name);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }
    public void open_Sdash(String Name, int Lrn){
        Intent intent = new Intent(this, StudentDash.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name", Name);
        bundle.putInt("Lrn", Lrn);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }


}