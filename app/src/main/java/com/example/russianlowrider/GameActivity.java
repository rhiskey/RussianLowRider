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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    // The what values of the messages
    private static final int UPDATE = 0x00;
    private static final int RESET_SCORE = 0x01;

    private static com.example.russianlowrider.GameView gameView;
    private TextView textViewScore;
    private LinearLayout inGameButtons;

    private static boolean isGameOver;
    private boolean isSetNewTimerThreadEnabled;

    private Thread setNewTimerThread;
    private static AlertDialog.Builder alertDialog;
    private MediaPlayer mediaPlayer;
    private MediaPlayer bgMusic;

    private static Timer timer;

    // Жизненный цикл игры
    private static class GameHandler extends Handler {
        //Используем слабую ссылку референс , чтобы не прерывать сборку мусора
        private final WeakReference<GameActivity> GameClassWeakReference;

        public GameHandler(GameActivity myClassInstance) {
            GameClassWeakReference = new WeakReference<GameActivity>(myClassInstance);
        }

        // Handler handler =
        public void handleMessage(@NotNull Message message) {
            final GameActivity GameClass = GameClassWeakReference.get();
            if (GameClass != null) {
                switch (message.what) {
                    case UPDATE: {
                        if (gameView.isCanDrive()) {
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


                            alertDialog = new AlertDialog.Builder(GameClass);
                            alertDialog.setTitle("GAME OVER");
                            alertDialog.setMessage("Score: " + gameView.getScore() +
                                    "\n" + "Would you like to RESTART?");
                            alertDialog.setCancelable(false);
                            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    GameClass.restartGame();
                                }
                            });
                            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    GameClass.onBackPressed();
                                }
                            });
                            alertDialog.show();
                        }

                        break;
                    }

                    case RESET_SCORE: {
                        GameClass.textViewScore.setText("0");

                        break;
                    }

                    default: {
                        break;
                    }
                }
            }
        }
    }

    private final GameHandler handler = new GameHandler(this);

    public GameHandler getHandler() {
        return new GameHandler(this);
    }

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

        //TODO МУЗЫКУ ПОЖАТЬ ШАКАЛАМИ ДО 30 сек
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

        //Buttons Listener

    }

    private void initViews() {
        gameView = findViewById(R.id.game_view);
        textViewScore = findViewById(R.id.text_view_score);
        inGameButtons = findViewById(R.id.linearLayoutBtns);
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
            //Handler handler = new GameHandler();
            @Override
            public void run() {
                // Send the message to the handler to update the UI of the GameView
                handler.sendEmptyMessage(UPDATE);

                // For garbage collection
                System.gc();
            }
//TODO стояло 17, что за период?ы
        }, 0, 5);
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }


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
            //Handler handler = new GameHandler();
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

    //TODO забить на хуйню с вращением, сделать именно механику с высотой, это дело перенести в
    //GameVIEW
/*
    public void onLeftBtnClick(View view) {
        //Поднять зад пневму
        //gameView.setPneumo();
    }

    public void onRightBtnClick(View view) {
        //Поднять перед пневму
        //gameView.setPneumo();
    }*/


}
