package com.example.sanauno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.sanauno.database.InstructorManager;
import com.example.sanauno.sng.InClassInfo;

public class TeacherViewAssignment extends AppCompatActivity {

    private InstructorManager iManager;

    private ListView lv;
    private SimpleCursorAdapter scAdapter;

    private InClassInfo insInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_assignment);

        

    }
}