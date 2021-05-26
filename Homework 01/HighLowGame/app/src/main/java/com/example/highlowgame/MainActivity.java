package com.example.highlowgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                int guess = Integer.parseInt(string_guess);
                if (guess == answer) {
                    Toast ansToast = Toast.makeText(getApplicationContext(), "Congratulations! Your input is correct", Toast.LENGTH_SHORT);
                    View toastView = ansToast.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toast_green));
                    ansToast.show();
                } else if (guess < answer) {
                    Toast lowToast = Toast.makeText(getApplicationContext(), "Your guess is too low", Toast.LENGTH_SHORT);
                    View toastView = lowToast.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toast_red));
                    lowToast.show();
                } else {
                    Toast highToast = Toast.makeText(getApplicationContext(), "Your guess is too high", Toast.LENGTH_SHORT);
                    View toastView = highToast.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toast_red));
                    highToast.show();
                }
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
            }
        });
    }
}