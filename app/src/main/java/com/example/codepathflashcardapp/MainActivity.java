package com.example.codepathflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView flashcardQuestion = findViewById(R.id.flashcardQuestion);
        final TextView flashcardAnswer = findViewById(R.id.flashcardAnswer);

        final TextView flashcardAnswer1 = findViewById(R.id.flashcardAnswer1);
        final TextView flashcardAnswer2 = findViewById(R.id.flashcardAnswer2);
        final TextView flashcardAnswer3 = findViewById(R.id.flashcardAnswer3);

        ImageView addFlashcard = findViewById(R.id.plus_icon);
        ImageView editFlashcard = findViewById(R.id.edit_icon);

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

        addFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, REQUEST_CODE);
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));
            }
        });

        editFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question", flashcardQuestion.getText().toString());
                intent.putExtra("answer", flashcardAnswer.getText().toString());
                intent.putExtra("wrongAnswer1", flashcardAnswer1.getText().toString());
                intent.putExtra("wrongAnswer2", flashcardAnswer3.getText().toString());
                MainActivity.this.startActivityForResult(intent, REQUEST_CODE);
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        if (requestCode == 20) {
            final TextView flashcardQuestion = findViewById(R.id.flashcardQuestion);
            final TextView flashcardAnswer = findViewById(R.id.flashcardAnswer);

            final TextView flashcardAnswer1 = findViewById(R.id.flashcardAnswer1);
            final TextView flashcardAnswer2 = findViewById(R.id.flashcardAnswer2);
            final TextView flashcardAnswer3 = findViewById(R.id.flashcardAnswer3);

            String question = data.getExtras().getString("question");
            String answer = data.getExtras().getString("answer");
            String wrongAnswer1 = data.getExtras().getString("wrongAnswer1");
            String wrongAnswer2 = data.getExtras().getString("wrongAnswer2");

            flashcardQuestion.setText(question);
            flashcardAnswer.setText(answer);
            flashcardAnswer1.setText(wrongAnswer1);
            flashcardAnswer2.setText(answer);
            flashcardAnswer3.setText(wrongAnswer2);

            Snackbar.make(findViewById(R.id.flashcardQuestion),
                    "Flashcard added!",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}
