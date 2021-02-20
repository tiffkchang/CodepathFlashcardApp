package com.example.codepathflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView flashcardQuestion = findViewById(R.id.flashcardQuestion);
        final TextView flashcardAnswer = findViewById(R.id.flashcardAnswer);

        final TextView flashcardAnswer1 = findViewById(R.id.flashcardAnswer1);
        final TextView flashcardAnswer2 = findViewById(R.id.flashcardAnswer2);
        final TextView flashcardAnswer3 = findViewById(R.id.flashcardAnswer3);

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });

        flashcardAnswer.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);
            }
        }));

        flashcardAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
            }
        });

        flashcardAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
            }
        });

        flashcardAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
            }
        });
    }
}
