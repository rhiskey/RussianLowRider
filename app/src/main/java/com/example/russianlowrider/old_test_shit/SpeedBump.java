package com.example.russianlowrider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

// Лежак
public class SpeedBump extends GameActor {
    // Velocity of game character (pixel/millisecond)
    public static final float VELOCITY = 0.2f;

    private int movingVectorX = -10;
    private int movingVectorY = 0;

    private long lastDrawNanoTime = -1;

    //private GameSurface gameSurface;

    private Bitmap image;

    // Каждый лежак должен ехать по X справа налево с определенной скоростью
    public SpeedBump(Bitmap image, int x, int y, float velocity) {
        super(image, 5, 5, velocity);
        //TODO
        //this.gameSurface = gameSurface;
        this.image = image;
        //Bitmap draw

        //init(context);
    }

    public Bitmap getBitmap() {
        return this.image;
    }

    public void setBitmap(Bitmap imageToSet) {
        this.image = imageToSet;
    }

    //Проверка столкновения с машиной
    public boolean isCollision(float carX, float carY, float carSize) {
        //TODO
        //return !(((x+size) < carX)||(x > (carX+carSize))||((y+size) < carY)||(y > (carY+carSize)));
        return false;
    }


    public void update() {
        //TODO

/*        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never once did draw.
        if (lastDrawNanoTime == -1) {
            lastDrawNanoTime = now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int deltaTime = (int) ((now - lastDrawNanoTime) / 1000000);

        // Distance moves
        float distance = VELOCITY * deltaTime;

        double movingVectorLength = movingVectorX;

        // Calculate the new position of the game character.
        this.x = x + (int) (distance * movingVectorX / movingVectorLength);
        this.y = y + (int) (distance * movingVectorY / movingVectorLength);*/


    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
        // Last draw time.
        this.lastDrawNanoTime = System.nanoTime();
    }

    public void setMovingVector(int movingVectorX, int movingVectorY) {
        this.movingVectorX = movingVectorX;
        this.movingVectorY = movingVectorY;
    }
}
