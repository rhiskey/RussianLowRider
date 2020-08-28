package com.example.russianlowrider;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    private static int EXPECTED_FPS = 30;
    private static final long TIME_BETWEEN_DRAWS = 1000 / EXPECTED_FPS;

    /**
     * Sets the Timer to update the UI of the GameView.
     */


    private void setNewTimer() {
        if (!isSetNewTimerThreadEnabled) {
            return;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            //Handler handler = new GameHandler();
            public Runnable handlerRun = new Runnable() {
                @Override
                public void run() {
                    // Send the message to the handler to update the UI of the GameView
                    handler.sendEmptyMessage(UPDATE);

                    // For garbage collection
                    System.gc();
                }
            };

            @Override
            public void run() {
                handlerRun.run();
            }

//TODO  Время обновления игры
        }, 0, TIME_BETWEEN_DRAWS);

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
        bgMusic = MediaPlayer.create(this, R.raw.moon_dust);
        bgMusic.setLooping(true);

        bgMusic.start();

        // Set the Timer
        isSetNewTimerThreadEnabled = true;
        setNewTimerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Sleep for 2 seconds for the Surface to initialize
                    Thread.sleep(2000);
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

        // Jump listener
        //Разделить на левую часть экрана и правую. Левая- задняя ось, правая - перед

        gameView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                float halfOfAScreen = width / 2;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        float fingerPosition = motionEvent.getX();
                        if (fingerPosition < halfOfAScreen) {
                            //Левая пневма
                            gameView.LeftPneumaPressed();
                            //onBackPressed();

                        } else {
                            gameView.RightPneumaPressed();
                            // правая пневма
                        }
                        return true;
                    default:
                        return false;
                }

//                OLD SHIT

/*                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        gameView.jump();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;

                    default:
                        break;
                }

                return true;*/
            }
        });
    }


    private void initViews() {
        gameView = findViewById(R.id.game_view);

        textViewScore = findViewById(R.id.text_view_score);
        inGameButtons = findViewById(R.id.linearLayoutBtns);

        //поднять на слой за игрой
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
                try {
                    //Smooth BG Animation Looping
                    final ImageView backgroundOne = (ImageView) findViewById(R.id.bg1);
                    final ImageView backgroundTwo = (ImageView) findViewById(R.id.bg2);

                    //final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
                    animator.setRepeatCount(ValueAnimator.INFINITE);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.setDuration(40000L);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            final float progress = (float) animation.getAnimatedValue();
                            final float width = backgroundOne.getWidth();
                            final float translationX = width * (-progress);
                            backgroundOne.setTranslationX(translationX);
                            backgroundTwo.setTranslationX(translationX - width);
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    animator.start();
                }
            }
        }, 100);
        //smoothBGAnimation.start();


/*        final ImageView backgroundCity = (ImageView) findViewById(R.id.background_City);
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundCity.getWidth();
                final float translationX = width * progress;
                backgroundCity.setTranslationX(translationX);
                //backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();*/

    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }


        isSetNewTimerThreadEnabled = false;


        bgMusic.stop();
        bgMusic.seekTo(0);

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        isSetNewTimerThreadEnabled = false;
        bgMusic.pause();
        super.onPause();
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        bgMusic.seekTo(0);
        bgMusic.start();
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
                    // Sleep for 2 seconds
                    Thread.sleep(2000);
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

        bgMusic.seekTo(0);
        bgMusic.start();
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

    // Жизненный цикл игры
    private static class GameHandler extends Handler {
        //Используем слабую ссылку референс , чтобы не прерывать сборку мусора
        private final WeakReference<GameActivity> GameClassWeakReference;
//        long previousTimeMillis;
//        long currentTimeMillis;
//        long elapsedMillis;
//        //previousTimeMillis = System.currentTimeMillis();

        public GameHandler(GameActivity myClassInstance) {
            GameClassWeakReference = new WeakReference<GameActivity>(myClassInstance);
        }

        // Handler handler =
        public void handleMessage(@NotNull final Message message) {
            //В отдельный поток
            //Runnable handler = new Runnable() {
//                    @Override
//                    public void run() {

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


/*                            alertDialog = new AlertDialog.Builder(GameClass);
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
                            alertDialog.show();*/
                        }


                        break;
                    }

/*                    case RESET_SCORE: {
                        GameClass.textViewScore.setText("0");

                        break;
                    }*/

                    default: {
                        break;
                    }
                }
            }

//                    }
            // };
            //handler.run();
        }

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
