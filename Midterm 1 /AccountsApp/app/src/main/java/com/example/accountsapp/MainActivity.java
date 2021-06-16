// Bennie Chen
// ID: 112737201
// CSE 390: Mobile App Development
// Midterm 1

package com.example.accountsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Person> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);
        if((users = (ArrayList<Person>) getIntent().getSerializableExtra("users")) == null)
            users = new ArrayList<Person>();
        int position = getSharedPreferences("userPref", Context.MODE_PRIVATE).getInt("position", -1);
        if(position > -1){
            EditText username = findViewById(R.id.enterUsername);
            EditText password = findViewById(R.id.enterPassword);
            EditText fullname = findViewById(R.id.enterFullName);
            try {
                username.setText(users.get(position).getUsername());
                password.setText(users.get(position).getPassword());
                fullname.setText(users.get(position).getFullName());
            }catch(Exception e){

            }
        }
        initLoginButton();
        initSaveButton();
        initAddButton();
    }

    private void initAddButton(){
        ImageButton add = findViewById(R.id.AddButtonSeconda);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("userPref", Context.MODE_PRIVATE).edit().putInt("position", -1).commit();
                EditText username = findViewById(R.id.enterUsername);
                EditText password = findViewById(R.id.enterPassword);
                EditText fullname = findViewById(R.id.enterFullName);
                username.getText().clear();
                password.getText().clear();
                fullname.getText().clear();
            }
        });
    }

    private void initLoginButton(){
        ImageButton login = findViewById(R.id.LoginButtonSeconda);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccessActivity.class);
                intent.putExtra("users", users);
                startActivity(intent);
            }
        });
    }

    private void initSaveButton(){
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.enterUsername)).getText().toString();
                String password = ((EditText) findViewById(R.id.enterPassword)).getText().toString();
                String fullname = ((EditText) findViewById(R.id.enterFullName)).getText().toString();
                if(username.trim().matches("") || password.trim().matches("") || fullname.trim().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill all the Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                users.add(new Person(username.trim(), password.trim(), fullname.trim()));
                Toast.makeText(getApplicationContext(), "User has been saved", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}