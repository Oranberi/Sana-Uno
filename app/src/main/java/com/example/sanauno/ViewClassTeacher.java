package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.sanauno.database.DBHelper;
import com.example.sanauno.database.InstructorManager;
import com.example.sanauno.sng.InClassInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewClassTeacher extends AppCompatActivity {

    FloatingActionButton btnAddClass;
    TextView txtEmptyClass;

    private InstructorManager iManager;

    private ListView lv;
    private SimpleCursorAdapter scAdapter;

    private InClassInfo insInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_teacher);

        btnAddClass =  (FloatingActionButton) findViewById(R.id.btnAddInstructorClass);
        lv = (ListView) findViewById(R.id.lvStudentList);
        txtEmptyClass = (TextView) findViewById(R.id.txtEmptyClass);

        insInfo = new InClassInfo();
        iManager = new InstructorManager(this);
        iManager.openDB();

        Bundle bundle = getIntent().getExtras();
        insInfo.setInstructor(bundle.getString("Name"));

        Cursor cursor = iManager.fetch_InstrucorClass(insInfo.getInstructor());

        String[] from = new String[]{DBHelper.CLM_ICNAME, DBHelper.CLM_CLASSCODE, DBHelper.CLM_ISSEMESTER, DBHelper.CLM_INID};
        int[] to = new int[]{R.id.txtClassName, R.id.txtClassCode, R.id.txtSemester};

        scAdapter = new SimpleCursorAdapter(this, R.layout.classteacher, cursor, from, to, 0);

        lv.setEmptyView(txtEmptyClass);
        lv.setAdapter(scAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ViewClassTeacher.this, TeacherExpandClass.class);
                Bundle bundle1 = new Bundle();
                Cursor cursor1 = (Cursor) scAdapter.getItem(i);

                insInfo.setId(cursor1.getString(0));
                insInfo.setClasscode(cursor1.getString(1));
                insInfo.setClassname(cursor1.getString(2));
                insInfo.setInstructor(cursor1.getString(4));

                bundle1.putString("ic_ID", insInfo.getId());
                bundle1.putString("icCode", insInfo.getClasscode());
                bundle1.putString("icName", insInfo.getClassname());
                bundle1.putString("cInstructor", insInfo.getInstructor());
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddInstructorClass(insInfo.getInstructor());
            }
        });
    }

    public void openAddInstructorClass(String name){
        Intent intent = new Intent(this, AddInstructorClass.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name", name);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}