package com.example.russianlowrider;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    // The what values of the messages
    private static final int UPDATE = 0x00;
    private static final int RESET_SCORE = 0x01;
    private GameView gameView;
    private TextView textViewScore;
    private boolean isGameOver;
    private boolean isSetNewTimerThreadEnabled;

    private Thread setNewTimerThread;
    private AlertDialog.Builder alertDialog;
    private MediaPlayer mediaPlayer;
    private MediaPlayer bgMusic;

    private Timer timer;

//TODO пиздец утечка памяти, нужно переделать нормально
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE: {
                    if (gameView.isAlive()) {
                        isGameOver = false;
                        gameView.update();
                    } else {
                        if (isGameOver) {
                            break;
                        } else {
                            isGameOver = true;
                        }
                        // Cancel the timer
                        timer.cancel();
                        timer.purge();


                        alertDialog = new AlertDialog.Builder(GameActivity.this);
                        alertDialog.setTitle("GAME OVER");
                        alertDialog.setMessage("Score: " + gameView.getScore() +
                                "\n" + "Would you like to RESTART?");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GameActivity.this.restartGame();
                            }
                        });
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GameActivity.this.onBackPressed();
                            }
                        });
                        alertDialog.show();
                    }

                    break;
                }

                case RESET_SCORE: {
                    textViewScore.setText("0");

                    break;
                }

                default: {
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        // Initialize the private views
        initViews();

        // Initialize the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_score);
        mediaPlayer.setLooping(false);

//        //.menuMusic.stop();

//        bgMusic = MediaPlayer.create(this, R.raw.moon_dust);
//        bgMusic.setLooping(true);
//
//        bgMusic.start();

        // Set the Timer
        isSetNewTimerThreadEnabled = true;
        setNewTimerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Sleep for 3 seconds for the Surface to initialize
                    Thread.sleep(3000);
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    if (isSetNewTimerThreadEnabled) {
                        setNewTimer();
                    }
                }
            }
        });
        setNewTimerThread.start();

        // Jump listener
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        gameView.jump();

                        break;

                    case MotionEvent.ACTION_UP:


                        break;

                    default:
                        break;
                }

                return true;
            }
        });

    }

    private void initViews() {
        gameView = findViewById(R.id.game_view);
        textViewScore = findViewById(R.id.text_view_score);
    }

    /**
     * Sets the Timer to update the UI of the GameView.
     */
    private void setNewTimer() {
        if (!isSetNewTimerThreadEnabled) {
            return;
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // Send the message to the handler to update the UI of the GameView
                GameActivity.this.handler.sendEmptyMessage(UPDATE);

                // For garbage collection
                System.gc();
            }

        }, 0, 17);
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

/*        if (audioRecorder != null) {
            audioRecorder.isGetVoiceRun = false;
            audioRecorder = null;
        }*/

        isSetNewTimerThreadEnabled = false;


//        bgMusic.stop();
//        bgMusic.seekTo(0);

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        isSetNewTimerThreadEnabled = false;
//        bgMusic.pause();
        super.onPause();
    }

    @Override
    protected void onRestart() {

        super.onRestart();
//        bgMusic.seekTo(0);
//        bgMusic.start();
    }

    /**
     * Updates the displayed score.
     *
     * @param score The new score.
     */
    public void updateScore(int score) {
        textViewScore.setText(String.valueOf(score));
    }

    /**
     * Plays the music for score.
     */
    public void playScoreMusic() {
        mediaPlayer.start();
    }

    /**
     * Restarts the game.
     */
    private void restartGame() {
        // Reset all the data of the over game in the GameView
        gameView.resetData();

        // Refresh the TextView for displaying the score
        new Thread(new Runnable() {

            @Override
            public void run() {
                handler.sendEmptyMessage(RESET_SCORE);
            }

        }).start();

        isSetNewTimerThreadEnabled = true;
        setNewTimerThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // Sleep for 3 seconds
                    Thread.sleep(3000);
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    if (isSetNewTimerThreadEnabled) {
                        setNewTimer();
                    }
                }
            }

        });
        setNewTimerThread.start();

//        bgMusic.seekTo(0);
//        bgMusic.start();
    }

    @Override
    public void onBackPressed() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        isSetNewTimerThreadEnabled = false;

        super.onBackPressed();
    }

    //TODO забить на хуйню с вращением, сделать именно механику с высотой
    public void onLeftBtnClick(View view) {
        //Поднять зад пневму
        gameView.setPneumo();
    }

    public void onRightBtnClick(View view) {
        //Поднять перед пневму
        gameView.setPneumo();
    }


}
