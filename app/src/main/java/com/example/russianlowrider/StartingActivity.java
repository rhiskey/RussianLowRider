package com.example.russianlowrider;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

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


 /*       Canvas canvas = new Canvas();
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        //Scale Images
        float height = bg.getHeight();
        float width = bg.getWidth();
        float ratio = width / height;
        newHeight = screenHeight;
        newWidth = (int) (ratio * screenHeight);

        bg = Bitmap.createScaledBitmap(bg, newWidth, newHeight, false);
        //Эффект скроллинга
        bgX -= 1; //Чем больше число тем быстрее
        if (bgX < -newWidth) {
            bgX = 0;
        }
        canvas.drawBitmap(bg, bgX, 0, null);
        if (bgX < screenWidth - newWidth) {
            canvas.drawBitmap(bg, bgX + newWidth, 0, null);
        }*/
/*
        menuMusic = MediaPlayer.create(this, R.raw.admo_aftershock);
        menuMusic.setLooping(true);
        menuMusic.seekTo(0);
        menuMusic.start();*/
    }

    public void goToTouchActivity(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("Mode", "Touch");
        startActivity(intent);
    }
}
