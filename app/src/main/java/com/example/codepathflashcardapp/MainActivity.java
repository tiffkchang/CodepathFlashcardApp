package com.example.codepathflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.plattysoft.leonids.ParticleSystem;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    private final int REQUEST_CODE = 20;
    private final int EDIT_REQUEST_CODE = 30;
    CountDownTimer countDownTimer;

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
                flashcardQuestion.setCameraDistance(25000);
                flashcardAnswer.setCameraDistance(25000);

                flashcardQuestion.animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        flashcardQuestion.setVisibility(View.INVISIBLE);
                                        flashcardAnswer.setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        flashcardAnswer.setRotationY(-90);
                                        flashcardAnswer.animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
            }
        });

        flashcardAnswer.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setCameraDistance(25000);
                flashcardAnswer.setCameraDistance(25000);

                flashcardAnswer.animate()
                        .rotationY(-90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        flashcardAnswer.setVisibility(View.INVISIBLE);
                                        flashcardQuestion.setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        flashcardQuestion.setRotationY(90);
                                        flashcardQuestion.animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
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
                new ParticleSystem(MainActivity.this, 40, R.drawable.confetti3, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.flashcardAnswer2), 100);
                new ParticleSystem(MainActivity.this, 40, R.drawable.confetti, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.flashcardAnswer2), 100);
                new ParticleSystem(MainActivity.this, 40, R.drawable.confetti2, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.flashcardAnswer2), 100);
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                        currentCardDisplayedIndex++;
                        flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                        flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                        flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        flashcardQuestion.startAnimation(rightInAnim);
                        flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                        flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        startTimer();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                if (currentCardDisplayedIndex + 1 < allFlashcards.size()) {
                    flashcardQuestion.startAnimation(leftOutAnim);
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
                final Animation leftInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_in);
                final Animation rightOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_out);

                rightOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                        currentCardDisplayedIndex--;
                        flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                        flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                        flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        flashcardQuestion.startAnimation(leftInAnim);
                        flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                        flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        startTimer();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });


                if (currentCardDisplayedIndex - 1 > -1) {
                    flashcardQuestion.startAnimation(rightOutAnim);
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
                startTimer();
                if (currentCardDisplayedIndex - 1 > -1) {
                    currentCardDisplayedIndex--;
                    flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                    flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                    flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                    flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                    flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));
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
                    startTimer();
                    Collections.shuffle(allFlashcards);
                    currentCardDisplayedIndex = 0;
                    flashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    flashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                    flashcardAnswer2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardAnswer3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                    flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                    flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                    flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));
                }
            }
        });

        countDownTimer = new CountDownTimer(16000, 1000) {
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.timer)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
            }
        };

        startTimer();
    }

    private void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
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

        flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.answerOutline));
        flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.answerOutline));
        flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.answerOutline));

        if (requestCode == REQUEST_CODE) {
            currentCardDisplayedIndex = allFlashcards.size();

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
