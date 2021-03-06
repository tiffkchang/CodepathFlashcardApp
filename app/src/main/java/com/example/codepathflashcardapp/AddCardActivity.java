package com.example.codepathflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        final EditText questionInput = findViewById(R.id.questionInput);
        final EditText answerInput = findViewById(R.id.answerInput);
        final EditText wrongAnswerInput1 = findViewById(R.id.wrongAnswerInput1);
        final EditText wrongAnswerInput2 = findViewById(R.id.wrongAnswerInput2);
        ImageView saveFlashcard = findViewById(R.id.save_icon);
        ImageView cancelFlashcard = findViewById(R.id.cancel_icon);

        questionInput.setText(getIntent().getStringExtra("question"));
        answerInput.setText(getIntent().getStringExtra("answer"));
        wrongAnswerInput1.setText(getIntent().getStringExtra("wrongAnswer1"));
        wrongAnswerInput2.setText(getIntent().getStringExtra("wrongAnswer2"));

        cancelFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionInput.getText().length() <= 0 || answerInput.getText().length() <= 0 || wrongAnswerInput1.getText().length() <= 0 || wrongAnswerInput2.getText().length() <= 0) {
                    Toast.makeText(AddCardActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(AddCardActivity.this, MainActivity.class);
                i.putExtra("question", questionInput.getText().toString());
                i.putExtra("answer", answerInput.getText().toString());
                i.putExtra("wrongAnswer1", wrongAnswerInput1.getText().toString());
                i.putExtra("wrongAnswer2", wrongAnswerInput2.getText().toString());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
