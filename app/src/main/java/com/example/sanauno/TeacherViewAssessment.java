package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.sanauno.database.AssessManager;
import com.example.sanauno.database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TeacherViewAssessment extends AppCompatActivity {

    ListView lvAssessment;
    SimpleCursorAdapter scAdapter;
    FloatingActionButton btnAddAssessment;

    AssessManager aManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_assessment);

        lvAssessment = (ListView) findViewById(R.id.lvAssessment);
        btnAddAssessment = (FloatingActionButton) findViewById(R.id.btnAddAssessment);

        Bundle bundle = getIntent().getExtras();
        int ins_id = bundle.getInt("ins_ID");
        int id = ins_id;
        Toast.makeText(this, ""+ins_id, Toast.LENGTH_SHORT).show();

        aManager = new AssessManager(this);
        aManager.openDB();
        Cursor cursor = aManager.fecthAllAssessment(ins_id);

        String[] from = new String[]{DBHelper.CLM_ASSESSTITLE, DBHelper.CLM_ASSESSLRN, DBHelper.CLM_ASSESSSCORE};
        int[] to = new int[]{R.id.txtWorkName, R.id.txtStudentName, R.id.txtStudentScore};
        scAdapter =  new SimpleCursorAdapter(this,R.layout.worklist, cursor, from, to, 0);
        lvAssessment.setAdapter(scAdapter);

        lvAssessment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TeacherViewAssessment.this, EditWork.class);
                Bundle bundle1 = new Bundle();
                Cursor cursor1 = (Cursor) scAdapter.getItem(i);

                String assessID = cursor1.getString(0);
                String assessTitle = cursor1.getString(1);
                String assessLrn = cursor1.getString(3);
                String assessScore = cursor1.getString(5);
                String assessItem = cursor1.getString(4);

                bundle1.putInt("worktype", 1);
                bundle1.putString("ID", assessID);
                bundle1.putString("Title", assessTitle);
                bundle1.putString("Lrn", assessLrn);
                bundle1.putString("Score", assessScore);
                bundle1.putString("Item", assessItem);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btnAddAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddAssess(id);
            }
        });
    }

    void openAddAssess(int insID){
        Intent intent = new Intent(this, AddWork.class);
        Bundle bundle = new Bundle();
        bundle.putInt("insID", insID);
        bundle.putInt("work", 1);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}