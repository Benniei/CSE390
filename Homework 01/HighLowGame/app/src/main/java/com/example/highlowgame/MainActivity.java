package com.example.highlowgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initiate the correct answer
        answer = ((int) (Math.random() * 10)) + 1;
        setContentView(R.layout.activity_main);
        initGuessButton();
        initAnswerButton();
        initNewGameButton();
    }

    private void initGuessButton() {
        Button guessButton = findViewById(R.id.guess_button);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText guess_input = findViewById(R.id.guess_input);
                String string_guess = guess_input.getText().toString();
                if (string_guess.matches("")) {
                    Toast niToast = Toast.makeText(getApplicationContext(), "You did not provide a guess", Toast.LENGTH_SHORT);
                    View toastView = niToast.getView();
                    niToast.show();
                    return;
                }
                TextView counter = findViewById(R.id.num_count);
                String countTries = counter.getText().toString();
                Integer count = Integer.parseInt(countTries.toString());
                TextView notice = findViewById(R.id.notify);
                if(count == 0){
                    notice.setText("There are no more tries left");
                    notice.setTextColor(getResources().getColor(R.color.toast_red));
                    return;
                }
                int guess = Integer.parseInt(string_guess);
                if(guess < 0 || guess > 10){
                    notice.setText("Input is out of bounds");
                    notice.setTextColor(getResources().getColor(R.color.toast_red));
                }
                else if (guess == answer) {
                    notice.setText("Congratulations! Your input is correct");
                    notice.setTextColor(getResources().getColor(R.color.toast_green));
                    return;
                }
                else if (guess < answer) {
                    notice.setText("Your guess is too low");
                    notice.setTextColor(getResources().getColor(R.color.toast_red));
                }
                else {
                    notice.setText("Your guess is too high");
                    notice.setTextColor(getResources().getColor(R.color.toast_red));
                }
                count--;
                counter.setText(count.toString());
            }
        });

    }

    private void initAnswerButton() {
        Button answerButton = findViewById(R.id.answer_button);

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast ansToast = Toast.makeText(getApplicationContext(), String.format("The answer is %d", answer), Toast.LENGTH_SHORT);
                View toastView = ansToast.getView();
                toastView.setBackgroundColor(getResources().getColor(R.color.toast_yellow));
                ansToast.show();
                TextView counter = findViewById(R.id.num_count);
                counter.setText("0");
                TextView notice = findViewById(R.id.notify);
                notice.setText("");
            }
        });
    }

    private void initNewGameButton() {
        Button newGameButton = findViewById(R.id.new_game);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = ((int) (Math.random() * 10)) + 1;
                Toast ansToast = Toast.makeText(getApplicationContext(), "New Game Initiated", Toast.LENGTH_SHORT);
                View toastView = ansToast.getView();
                toastView.setBackgroundColor(getResources().getColor(R.color.purple_200));
                ansToast.show();
                TextView counter = findViewById(R.id.num_count);
                counter.setText("5");
                TextView notice = findViewById(R.id.notify);
                notice.setText("");
            }
        });
    }
}