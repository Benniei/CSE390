package com.example.sharedpreference;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SharedPF extends AppCompatActivity {
    ArrayList<Classes> courses;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedpf);
        courses = (ArrayList<Classes>) getIntent().getSerializableExtra("courses");
        initCancelButton();
        initSaveButton();
    }

    private void initCancelButton(){
        Button cancel = findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SharedPF.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("courses", courses);
                startActivityIfNeeded(intent, 0);
            }
        });
    }

    private void initSaveButton(){
        Button save = findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = ((EditText) findViewById(R.id.NameField)).getText().toString();
                String courseTitle = ((EditText) findViewById(R.id.Description)).getText().toString();
                String courseTime = ((EditText) findViewById(R.id.Time)).getText().toString();
                getSharedPreferences("ClassPreferences", Context.MODE_PRIVATE).edit().putString("courseName", courseName).commit();
                getSharedPreferences("ClassPreferences", Context.MODE_PRIVATE).edit().putString("courseTitle", courseTitle).commit();
                getSharedPreferences("ClassPreferences", Context.MODE_PRIVATE).edit().putString("courseTime", courseTime).commit();
                Intent intent = new Intent(SharedPF.this, MainActivity.class);
                intent.putExtra("courses", courses);
                startActivity(intent);
            }
        });
    }
}
