package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanauno.database.AssessManager;
import com.example.sanauno.database.DBHelper;
import com.example.sanauno.database.ExamManager;
import com.example.sanauno.database.QuizManager;
import com.google.android.material.textfield.TextInputLayout;

public class AddWork extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    AssessManager aManager;
    QuizManager qManager;
    ExamManager eManager;

    TextInputLayout txtWorkTitleLayout, txtWorkItemLayout;
    EditText txtAssessmentTitle, txtAssessmentItem;
    Button btnAddAssessNow, btnCancelWork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        txtWorkTitleLayout = findViewById(R.id.txtWorkTitleLayout);
        txtWorkItemLayout = findViewById(R.id.txtWorkItemLayout);
        btnCancelWork = findViewById(R.id.btnCancelWork);

        txtAssessmentItem = txtWorkItemLayout.getEditText();
        txtAssessmentTitle = txtWorkTitleLayout.getEditText();
        btnAddAssessNow = (Button) findViewById(R.id.btnAddAssessNow);

        Bundle bundle = getIntent().getExtras();
        int insID = bundle.getInt("insID");
        int work = bundle.getInt("work");
        Toast.makeText(this, ""+work, Toast.LENGTH_SHORT).show();

        btnAddAssessNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String title = txtAssessmentTitle.getText().toString();
                    int item = Integer.parseInt(txtAssessmentItem.getText().toString());
                    checknAddWork(""+insID,title,item,work);
                    finish();
                }
            }
        );
        btnCancelWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void checknAddWork(String insID, String title, int item, int work){
        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getReadableDatabase();
        String[] clm = new String[]{dbHelper.CLM_CID, dbHelper.CLM_CNAME, dbHelper.CLM_CCODE, dbHelper.CLM_SEMESTER, dbHelper.CLM_CLRN, dbHelper.CLM_CLINSTRUCTOR, dbHelper.CLM_CINSNAME};
        //String selection = dbHelper.CLM_LRN +" =?";
        String[] selectionArgs = new String[]{insID};
        Cursor cursor = sqLiteDatabase.query(dbHelper.TBL_CLASS, clm, dbHelper.CLM_CLINSTRUCTOR +" =?", selectionArgs,null,null,null);
        int num = cursor.getCount();


        if (cursor.moveToFirst()){
            do{
                int studentLRN = cursor.getInt(4);
                int id = Integer.parseInt(insID);
                switch (work){
                    case 1:
                        Toast.makeText(this, "Done" + num, Toast.LENGTH_SHORT).show();
                        aManager = new AssessManager(this);
                        aManager.openDB();
                        aManager.insertWork(title,studentLRN, id, item,0,"off");
                        break;
                    case 2:
                        Toast.makeText(this, "done " + num, Toast.LENGTH_SHORT).show();
                        qManager = new QuizManager(this);
                        qManager.openDB();
                        qManager.insertWork(title,studentLRN, id, item,0,"off");
                        break;
                    case 3:
                        eManager = new ExamManager(this);
                        eManager.openDB();
                        eManager.insertWork(title,studentLRN, id, item,0,"off");
                        break;
                }

            } while (cursor.moveToNext());
        }
    }

}