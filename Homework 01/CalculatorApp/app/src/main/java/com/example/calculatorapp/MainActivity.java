package com.example.calculatorapp;

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
        initAddButton();
        initSubtractButton();
        initMultiplyButton();
        initDivideButton();
    }

    private void initAddButton(){
        Button addButton = findViewById(R.id.plus);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputOne = findViewById(R.id.inputOne);
                EditText inputTwo = findViewById(R.id.inputTwo);
                String inOne = inputOne.getText().toString();
                String inTwo = inputTwo.getText().toString();
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

    private void initSubtractButton(){
        Button subButton = findViewById(R.id.subtract);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputOne = findViewById(R.id.inputOne);
                EditText inputTwo = findViewById(R.id.inputTwo);
                String inOne = inputOne.getText().toString();
                String inTwo = inputTwo.getText().toString();
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

    private void initMultiplyButton(){
        Button mulButton = findViewById(R.id.multiply);
        mulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputOne = findViewById(R.id.inputOne);
                EditText inputTwo = findViewById(R.id.inputTwo);
                String inOne = inputOne.getText().toString();
                String inTwo = inputTwo.getText().toString();
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

    private void initDivideButton(){
        Button divButton = findViewById(R.id.divide);
        divButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputOne = findViewById(R.id.inputOne);
                EditText inputTwo = findViewById(R.id.inputTwo);
                String inOne = inputOne.getText().toString();
                String inTwo = inputTwo.getText().toString();
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