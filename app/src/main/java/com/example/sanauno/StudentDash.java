package com.example.sanauno;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.sanauno.database.ClassManager;
import com.example.sanauno.database.DBHelper;
import com.example.sanauno.sng.ClassInfo;
import com.example.sanauno.sng.UserInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class StudentDash extends AppCompatActivity {

    TextView txtDStudentName, txtDStudentLRN, emptyStudentClass;
    FloatingActionButton btnEnrollClass;
    Button btnDStudentLogout;
    ListView lvStudentClassList;

    SimpleCursorAdapter scAdapter;

    ClassManager classManager;
    UserInfo uInfo;
    ClassInfo cInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash);

        txtDStudentName = (TextView) findViewById(R.id.txtDStudentName);
        txtDStudentLRN = (TextView) findViewById(R.id.txtDStudentLRN);
        emptyStudentClass = (TextView) findViewById(R.id.txtEmptyStudentClass);
        btnDStudentLogout = (Button) findViewById(R.id.btnDStudentLogout);
        btnEnrollClass = (FloatingActionButton) findViewById(R.id.btnEnrollClass);
        lvStudentClassList = (ListView) findViewById(R.id.lvStudentClassList);
        AlertDialog.Builder confirmLogout = new AlertDialog.Builder(StudentDash.this);
        uInfo = new UserInfo();
        cInfo = new ClassInfo();
        classManager = new ClassManager(this);
        classManager.openDB();

        Bundle bundle = getIntent().getExtras();
        uInfo.setName(bundle.getString("Name"));
        uInfo.setLrn(bundle.getInt("Lrn"));
        String lrn = ""+uInfo.getLrn();

        txtDStudentName.setText(uInfo.getName());
        txtDStudentLRN.setText(String.valueOf(uInfo.getLrn()));

        Cursor cursor = classManager.fetch_ClassStudent(lrn);

        String[] from = new String[]{DBHelper.CLM_CCODE, DBHelper.CLM_CNAME, DBHelper.CLM_SEMESTER};
        int [] to = new int[]{R.id.txtStuClassCode, R.id.txtStuClassName, R.id.txtStuSemester};
        scAdapter = new SimpleCursorAdapter(this, R.layout.classesstudent, cursor, from, to, 0);
        lvStudentClassList.setAdapter(scAdapter);


        btnEnrollClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEnrollClass(uInfo.getLrn());
            }
        });

        btnDStudentLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmLogout.setTitle("Log-out")
                        .setMessage("Do you want to log-out?")
                        .setCancelable(true)
                        .setPositiveButton("Log-out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(StudentDash.this, Choose.class);
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

        lvStudentClassList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(StudentDash.this, StudentExpandClass.class);
                Bundle bundle1 = new Bundle();
                Cursor cursor1 = (Cursor) scAdapter.getItem(i);


                String cID = cursor1.getString(0);
                String cCode = cursor1.getString(2);
                String cName = cursor1.getString(1);
                String cIns = cursor1.getString(6);
                String finalGrade = cursor1.getString(7);

                bundle1.putString("cID", cID);
                bundle1.putString("cCode", cCode);
                bundle1.putString("cName", cName);
                bundle1.putString("cIns", cIns);
                bundle1.putInt("lrn", Integer.parseInt(lrn));
                bundle1.putString("fGrade", finalGrade);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

    }

    public void openEnrollClass(int lrn){
        Intent intent = new Intent(this,StudentAddClass.class);
        Bundle bundle = new Bundle();
        bundle.putInt("lrn",lrn);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}