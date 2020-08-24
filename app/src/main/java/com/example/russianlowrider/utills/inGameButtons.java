package com.example.russianlowrider.utills;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.graphics.drawable.Drawable;

import com.example.russianlowrider.GameView;

public class inGameButtons extends View {
    static int h, w;

    public inGameButtons(Context context) {
        super(context);
    }

    public static Bitmap inGameButtonLeft(Resources res, int id) {
        Bitmap bt1;
        bt1 = BitmapFactory.decodeResource(res, id);
        bt1 = Bitmap.createScaledBitmap(bt1, w, h, false);
        return bt1;
    }

    public static Bitmap inGameButtonRight(Resources res, int id) {
        Bitmap bt2;
        bt2 = BitmapFactory.decodeResource(res, id);
        bt2 = Bitmap.createScaledBitmap(bt2, w, h, false);
        return bt2;
    }
}
