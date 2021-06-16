package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
   ArrayList<Classes> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if((courses = (ArrayList<Classes>) getIntent().getSerializableExtra("courses")) == null){
            courses = new ArrayList<Classes>();
        }
        initAddButton();
        initSearchButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String courseName = getSharedPreferences("ClassPreferences", Context.MODE_PRIVATE).getString("courseName", "");
        String courseTitle = getSharedPreferences("ClassPreferences", Context.MODE_PRIVATE).getString("courseTitle", "");
        String courseTime = getSharedPreferences("ClassPreferences", Context.MODE_PRIVATE).getString("courseTime", "");
        if(!courseName.equals("")){
            courses.add(new Classes(courseName, courseTitle, courseTime));
        }
    }


    private void initAddButton(){
        Button addButton = findViewById(R.id.otherPage);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SharedPF.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("courses", courses);
                startActivity(intent);
            }
        });
    }

    private void initSearchButton(){
        Button searchButton = findViewById(R.id.Search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classCode = ((EditText) findViewById(R.id.courseName)).getText().toString();
                TextView description = findViewById(R.id.DescirptionDisplay);
                TextView time = findViewById(R.id.TimeDisplay);
                for(Classes i: courses){
                    if(i.getClassCode().equals(classCode)){
                        description.setText(i.getClassTitle());
                        time.setText(i.getClassTime());
                        break;
                    }
                }
            }
        });
    }
}