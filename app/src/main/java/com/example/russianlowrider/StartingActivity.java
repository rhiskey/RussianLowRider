package com.example.russianlowrider;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

public class StartingActivity extends AppCompatActivity {
    public static MediaPlayer menuMusic;
//    int screenWidth, screenHeight, newWidth, newHeight;
//    int bgX = 0;
//    Bitmap bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starting);

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

/*
        menuMusic = MediaPlayer.create(this, R.raw.admo_aftershock);
        menuMusic.setLooping(true);
        menuMusic.seekTo(0);
        menuMusic.start();*/
    }

    // Нажали кнопку Старт
    public void goToTouchActivity(View view) {

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("Mode", "Touch");
        startActivity(intent);
    }


/*    //Повернули
    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        float density = getResources().getDisplayMetrics().density;
        int newScreenWidthPixels = (int) (newConfig.screenWidthDp * density);
        int newScreenHeightPixels = (int) (newConfig.screenHeightDp * density);

        // Get general orientation; either Configuration.ORIENTATION_PORTRAIT or
        // Configuration.ORIENTATION_LANDSCAPE.
        int newScreenOrientation = newConfig.orientation;

        // Get general rotation; one of: ROTATION_0, ROTATION_90, ROTATION_180,
        // or ROTATION_270.
        int newScreenRotation = getWindowManager().getDefaultDisplay()
                .getRotation();
    }*/
}
