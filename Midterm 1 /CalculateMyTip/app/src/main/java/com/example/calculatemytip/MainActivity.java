// Bennie Chen
// ID: 112737201
// CSE 390: Mobile App Development
// Midterm 1

package com.example.calculatemytip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFifteenButton();
        initEighteenButton();
    }

    private void initFifteenButton(){
        Button fifteen = findViewById(R.id.Fifteen);
        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.BillAmount);
                String cost = input.getText().toString().trim();
                if(cost.matches("")){
                    Toast niToast = Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT);
                    View toastView = niToast.getView();
                    niToast.show();
                    return;
                }
                double amount = Double.parseDouble(cost);
                TextView tipField = findViewById(R.id.Tip);
                TextView totalField = findViewById(R.id.Total);
                tipField.setText(String.format("Tip: %.2f", (amount * 0.15)));
                totalField.setText(String.format("Total: %.2f", (amount * 1.15)));
            }
        });
    }

    private void initEighteenButton(){
        Button eighteen = findViewById(R.id.Eighteeen);
        eighteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.BillAmount);
                String cost = input.getText().toString().trim();
                if(cost.matches("")){
                    Toast niToast = Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT);
                    View toastView = niToast.getView();
                    niToast.show();
                    return;
                }
                double amount = Double.parseDouble(cost);
                TextView tipField = findViewById(R.id.Tip);
                TextView totalField = findViewById(R.id.Total);
                tipField.setText(String.format("Tip: %.2f", (amount * 0.18)));
                totalField.setText(String.format("Total: %.2f", (amount * 1.18)));
            }
        });
    }

}