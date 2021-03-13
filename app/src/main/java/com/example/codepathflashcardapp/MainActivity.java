package com.example.codepathflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    private final int REQUEST_CODE = 20;
    private final int EDIT_REQUEST_CODE = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        final TextView flashcardQuestion = findViewById(R.id.flashcardQuestion);
        final TextView flashcardAnswer = findViewById(R.id.flashcardAnswer);

        final TextView flashcardAnswer1 = findViewById(R.id.flashcardAnswer1);
        final TextView flashcardAnswer2 = findViewById(R.id.flashcardAnswer2);
        final TextView flashcardAnswer3 = findViewById(R.id.flashcardAnswer3);

        ImageView addFlashcard = findViewById(R.id.plus_icon);
        ImageView editFlashcard = findViewById(R.id.edit_icon);
        ImageView nextButton = findViewById(R.id.next_icon);
        ImageView backButton = findViewById(R.id.back_icon);
        ImageView deleteButton = findViewById(R.id.delete_icon);
        ImageView shuffleButton = findViewById(R.id.shuffle_icon);

        if (allFlashcards.size() > 0) {
            flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
            flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
            flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
        } else {
            flashcardQuestion.setText("");
            flashcardAnswer.setText("");
            flashcardAnswer1.setText("");
            flashcardAnswer2.setText("");
            flashcardAnswer3.setText("");
        }

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
                MainActivity.this.startActivityForResult(intent, EDIT_REQUEST_CODE);
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCardDisplayedIndex + 1 < allFlashcards.size()) {
                    currentCardDisplayedIndex++;
                    flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                    flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                } else {
                    Snackbar.make(findViewById(R.id.flashcardQuestion),
                            "This is the last flashcard.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCardDisplayedIndex - 1 > -1) {
                    currentCardDisplayedIndex--;
                    flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                    flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                } else {
                    Snackbar.make(findViewById(R.id.flashcardQuestion),
                            "This is the first flashcard.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                allFlashcards = flashcardDatabase.getAllCards();
                if (currentCardDisplayedIndex - 1 > -1) {
                    currentCardDisplayedIndex--;
                    flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                    flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                } else {
                    flashcardQuestion.setText("");
                    flashcardAnswer.setText("");
                    flashcardAnswer1.setText("");
                    flashcardAnswer2.setText("");
                    flashcardAnswer3.setText("");
                }
            }
        });

        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() > 0) {
                    Collections.shuffle(allFlashcards);
                    currentCardDisplayedIndex = 0;
                    flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                    flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

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

        if (requestCode == REQUEST_CODE) {
            flashcardDatabase.insertCard(new Flashcard(question, answer, wrongAnswer1, wrongAnswer2));
            allFlashcards = flashcardDatabase.getAllCards();

            Snackbar.make(findViewById(R.id.flashcardQuestion),
                    "Flashcard added!",
                    Snackbar.LENGTH_SHORT)
                    .show();
        } else if (requestCode == EDIT_REQUEST_CODE) {
            Flashcard cardToEdit = allFlashcards.get(currentCardDisplayedIndex);
            cardToEdit.setQuestion(question);
            cardToEdit.setAnswer(answer);
            cardToEdit.setWrongAnswer1(wrongAnswer1);
            cardToEdit.setWrongAnswer2(wrongAnswer2);
            flashcardDatabase.updateCard(cardToEdit);
        }
    }
}
