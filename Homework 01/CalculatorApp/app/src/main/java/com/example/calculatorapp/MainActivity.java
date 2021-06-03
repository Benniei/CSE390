/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */
package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * This method is called when the app is created and initiates all the buttons and also sets up the layout
     *
     * @param savedInstanceState Bundle Object that passes in the saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAddButton();
        initSubtractButton();
        initMultiplyButton();
        initDivideButton();
    }

    /**
     * Initiates the add button which takes 2 inputs and computes addition
     */
    private void initAddButton(){
        Button addButton = findViewById(R.id.plus);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputOne = findViewById(R.id.inputOne);
                EditText inputTwo = findViewById(R.id.inputTwo);
                String inOne = inputOne.getText().toString().trim();
                String inTwo = inputTwo.getText().toString().trim();
                if(inOne.matches("") || inTwo.matches("")){
                    Toast niToast = Toast.makeText(getApplicationContext(), "Incomplete input", Toast.LENGTH_SHORT);
                    View toastView = niToast.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toast_red));
                    niToast.show();
                    return;
                }
                double one = Double.parseDouble(inOne);
                double two = Double.parseDouble(inTwo);
                TextView ansField = findViewById(R.id.final_ans);
                ansField.setText(String.format("%.2f", (one + two)));
            }
        });
    }

    /**
     * Initiates the subtract button which takes 2 inputs and computes subtraction
     */
    private void initSubtractButton(){
        Button subButton = findViewById(R.id.subtract);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputOne = findViewById(R.id.inputOne);
                EditText inputTwo = findViewById(R.id.inputTwo);
                String inOne = inputOne.getText().toString().trim();
                String inTwo = inputTwo.getText().toString().trim();
                if(inOne.matches("") || inTwo.matches("")){
                    Toast niToast = Toast.makeText(getApplicationContext(), "Incomplete input", Toast.LENGTH_SHORT);
                    View toastView = niToast.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toast_red));
                    niToast.show();
                    return;
                }
                double one = Double.parseDouble(inOne);
                double two = Double.parseDouble(inTwo);
                TextView ansField = findViewById(R.id.final_ans);
                ansField.setText(String.format("%.2f", (one - two)));
            }
        });
    }

    /**
     * Initiates the multiply button which takes 2 inputs and computes multiplication
     */
    private void initMultiplyButton(){
        Button mulButton = findViewById(R.id.multiply);
        mulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputOne = findViewById(R.id.inputOne);
                EditText inputTwo = findViewById(R.id.inputTwo);
                String inOne = inputOne.getText().toString().trim();
                String inTwo = inputTwo.getText().toString().trim();
                if(inOne.matches("") || inTwo.matches("")){
                    Toast niToast = Toast.makeText(getApplicationContext(), "Incomplete input", Toast.LENGTH_SHORT);
                    View toastView = niToast.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toast_red));
                    niToast.show();
                    return;
                }
                double one = Double.parseDouble(inOne);
                double two = Double.parseDouble(inTwo);
                TextView ansField = findViewById(R.id.final_ans);
                ansField.setText(String.format("%.2f", (one * two)));
            }
        });
    }

    /**
     * Initiates the divide button which takes 2 inputs and computer division (Checks for divide by 0)
     */
    private void initDivideButton(){
        Button divButton = findViewById(R.id.divide);
        divButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputOne = findViewById(R.id.inputOne);
                EditText inputTwo = findViewById(R.id.inputTwo);
                String inOne = inputOne.getText().toString().trim();
                String inTwo = inputTwo.getText().toString().trim();
                if(inOne.matches("") || inTwo.matches("")){
                    Toast niToast = Toast.makeText(getApplicationContext(), "Incomplete input", Toast.LENGTH_SHORT);
                    View toastView = niToast.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toast_red));
                    niToast.show();
                    return;
                }
                double one = Double.parseDouble(inOne);
                double two = Double.parseDouble(inTwo);
                if(two == 0){
                    Toast niToast = Toast.makeText(getApplicationContext(), "Divide By 0 error", Toast.LENGTH_SHORT);
                    View toastView = niToast.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toast_red));
                    niToast.show();
                    return;
                }
                TextView ansField = findViewById(R.id.final_ans);
                ansField.setText(String.format("%.2f", (one/two)));
            }
        });
    }
}