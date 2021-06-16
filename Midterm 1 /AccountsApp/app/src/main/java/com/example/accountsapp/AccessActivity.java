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

public class AccessActivity extends AppCompatActivity {
    ArrayList<Person> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users = (ArrayList<Person>) getIntent().getSerializableExtra("users");
        initAddButton();
        initLoginButton();
    }

    private void initAddButton(){
        ImageButton add = findViewById(R.id.AddButtonMain);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccessActivity.this, MainActivity.class);
                intent.putExtra("users", users);
                getSharedPreferences("userPref", Context.MODE_PRIVATE).edit().putInt("position", -1).commit();
                startActivity(intent);
            }
        });
    }

    private void initLoginButton(){
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) findViewById(R.id.loginUsername)).getText().toString();
                String password = ((EditText) findViewById(R.id.loginPassword)).getText().toString();
                if(username.trim().matches("") || password.trim().matches("")){
                    Toast.makeText(getApplicationContext(), "Please fill all the Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i = 0; i < users.size(); i++){
                    if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)){
                        getSharedPreferences("userPref", Context.MODE_PRIVATE).edit().putInt("position", i).commit();
                        Intent intent = new Intent(AccessActivity.this, MainActivity.class);
                        intent.putExtra("users", users);
                        startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}