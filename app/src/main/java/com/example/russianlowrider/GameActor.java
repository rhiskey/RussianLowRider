package com.example.russianlowrider;

import android.graphics.Bitmap;

public abstract class GameActor {
    protected final int WIDTH;
    protected final int HEIGHT;
    protected Bitmap image;
    protected int x;
    protected int y;
    protected float velocity;
/*    protected final int width;
    protected final int height;*/

    //x,y - position
    public GameActor(Bitmap image, int x, int y, float velocity) {

        this.image = image;

        this.x = x;
        this.y = y;
        this.velocity = velocity;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();


        //this.width = this.WIDTH / colCount;
        //this.height = this.HEIGHT / rowCount;
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getVelocity() {
        return (int) this.velocity;
    }

}
