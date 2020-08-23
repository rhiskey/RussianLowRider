package com.example.russianlowrider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Background {
    private GameSurface gameSurface;
    private Bitmap image;

    public Background(GameSurface gameSurface, Bitmap image) {
        super();
        this.gameSurface = gameSurface;
        this.image = image;
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(image);

        //Get screen Height and Width

        //Resize image, rotate

        canvas.drawBitmap(bitmap, 0, 0, null);
        // Last draw time.
        //this.lastDrawNanoTime = System.nanoTime();
    }
}
