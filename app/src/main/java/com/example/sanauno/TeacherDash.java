package com.example.sanauno;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sanauno.database.UserManager;
import com.example.sanauno.sng.UserInfo;

import org.w3c.dom.Text;

public class TeacherDash extends AppCompatActivity {

    ImageButton btnClass, btnStudent;
    Button btnLogout;
    TextView txtTeacherName;
    UserInfo uInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dash);

        btnClass = (ImageButton) findViewById(R.id.btnTClass);
        btnStudent = (ImageButton) findViewById(R.id.btnTStudent);
        btnLogout = (Button) findViewById(R.id.btnLogOut);
        txtTeacherName = (TextView) findViewById(R.id.txtTeacherName);

        uInfo = new UserInfo();
        AlertDialog.Builder confirmLogout = new AlertDialog.Builder(TeacherDash.this);

        Bundle bundle = getIntent().getExtras();
        uInfo.setName(bundle.getString("Name"));

        txtTeacherName.setText(uInfo.getName());


        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_Student();
            }
        });

        btnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open_Class(txtTeacherName.getText().toString());
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmLogout.setTitle("Log-out")
                        .setMessage("Do you want to log-out?")
                        .setCancelable(true)
                        .setPositiveButton("Log-out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(TeacherDash.this, Choose.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });

    }

    public void logOut(){

    }

    public void open_Class(String name){
        Intent intent = new Intent(this, ViewClassTeacher.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name", name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void open_Student(){
        Intent intent = new Intent(this, TeacherViewStudent.class);
        startActivity(intent);
    }

}