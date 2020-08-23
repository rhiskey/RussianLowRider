package com.example.russianlowrider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class VolgaCar extends GameActor {
    public final int size = 100;

    private GameSurface gameSurface;
    private long lastDrawNanoTime = -1;

    private Bitmap image;

    //TODO
    public VolgaCar(GameSurface gameSurface, Bitmap image, int x, int y, float velocity) {
        super(image, x, y, velocity);

    }

    public Bitmap getBitmap() {
        //this.image.setWidth(50);
        Bitmap resized = Bitmap.createScaledBitmap(this.image, 100, 50, true);

        return resized;
    }

    public void setBitmap(Bitmap imageToSet) {
        this.image = imageToSet;
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
        // Last draw time.
        this.lastDrawNanoTime = System.nanoTime();
    }
}



