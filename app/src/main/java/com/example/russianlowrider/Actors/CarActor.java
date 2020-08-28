package com.example.russianlowrider.Actors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class CarActor {
    //TODO Вынести логику машинки из GameView в этот класс

    private static final String TAG = CarActor.class.getSimpleName();

    private Bitmap bitmap;      // the animation sequence
    private Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    private int frameNr;        // number of frames in animation
    private int currentFrame;   // the current frame
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)

    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;   // the height of the sprite

    private int x;              // the X coordinate of the object (top left of the image)
    private int y;              // the Y coordinate of the object (top left of the image)

    public CarActor(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0l;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Rect getSourceRect() {
        return sourceRect;
    }

    public void setSourceRect(Rect sourceRect) {
        this.sourceRect = sourceRect;
    }

    public int getFrameNr() {
        return frameNr;
    }

    public void setFrameNr(int frameNr) {
        this.frameNr = frameNr;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getFramePeriod() {
        return framePeriod;
    }

    public void setFramePeriod(int framePeriod) {
        this.framePeriod = framePeriod;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // the update method for Elaine
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            // increment the frame
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }
        }
        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
    }

    // the draw method which draws the corresponding frame
    public void draw(Canvas canvas) {
        // where to draw the sprite
        Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
        canvas.drawBitmap(bitmap, 20, 150, null);
        Paint paint = new Paint();
        paint.setARGB(50, 0, 255, 0);
        canvas.drawRect(20 + (currentFrame * destRect.width()), 150, 20 + (currentFrame * destRect.width()) + destRect.width(), 150 + destRect.height(), paint);
    }



/*    private static int dinoBaseY, dinoTopY, dinoStartX, dinoEndX;
    private static int dinoTop, dinoBottom, topPoint;

    private static boolean topPointReached;
    private static int jumpFactor = 20;

    public static final int STAND_STILL = 1,
            RUNNING = 2,
            JUMPING = 3,
            DIE = 4;
    private final int LEFT_FOOT = 1,
            RIGHT_FOOT = 2,
            NO_FOOT = 3;

    private static int state;

    private int foot;

    static BufferedImage image;
    BufferedImage leftFootDino;
    BufferedImage rightFootDino;
    BufferedImage deadDino;

    public Car() {
        image = new Resource().getResourceImage("../images/Dino-stand.png");
        leftFootDino = new Resource().getResourceImage("../images/Dino-left-up.png");
        rightFootDino = new Resource().getResourceImage("../images/Dino-right-up.png");
        deadDino = new Resource().getResourceImage("../images/Dino-big-eyes.png");

        dinoBaseY = Ground.GROUND_Y + 5;
        dinoTopY = Ground.GROUND_Y - image.getHeight() + 5;
        dinoStartX = 100;
        dinoEndX = dinoStartX + image.getWidth();
        topPoint = dinoTopY - 120;

        state = 1;
        foot = NO_FOOT;
    }

    public void create(Graphics g) {
        dinoBottom = dinoTop + image.getHeight();

        // g.drawRect(getDino().x, getDino().y, getDino().width, getDino().height);

        switch(state) {

            case STAND_STILL:
                System.out.println("stand");
                g.drawImage(image, dinoStartX, dinoTopY, null);
                break;

            case RUNNING:
                if(foot == NO_FOOT) {
                    foot = LEFT_FOOT;
                    g.drawImage(leftFootDino, dinoStartX, dinoTopY, null);
                } else if(foot == LEFT_FOOT) {
                    foot = RIGHT_FOOT;
                    g.drawImage(rightFootDino, dinoStartX, dinoTopY, null);
                } else {
                    foot = LEFT_FOOT;
                    g.drawImage(leftFootDino, dinoStartX, dinoTopY, null);
                }
                break;

            case JUMPING:
                if(dinoTop > topPoint && !topPointReached) {
                    g.drawImage(image, dinoStartX, dinoTop -= jumpFactor, null);
                    break;
                }
                if(dinoTop >= topPoint && !topPointReached) {
                    topPointReached = true;
                    g.drawImage(image, dinoStartX, dinoTop += jumpFactor, null);
                    break;
                }
                if(dinoTop > topPoint && topPointReached) {
                    if(dinoTopY == dinoTop && topPointReached) {
                        state = RUNNING;
                        topPointReached = false;
                        break;
                    }
                    g.drawImage(image, dinoStartX, dinoTop += jumpFactor, null);
                    break;
                }
            case DIE:
                g.drawImage(deadDino, dinoStartX, dinoTop, null);
                break;
        }
    }

    public void die() {
        state = DIE;
    }

    public static Rectangle getDino() {
        Rectangle dino = new Rectangle();
        dino.x = dinoStartX;

        if(state == JUMPING && !topPointReached) dino.y = dinoTop - jumpFactor;
        else if(state == JUMPING && topPointReached) dino.y = dinoTop + jumpFactor;
        else if(state != JUMPING) dino.y = dinoTop;

        dino.width = image.getWidth();
        dino.height = image.getHeight();

        return dino;
    }

    public void startRunning() {
        dinoTop = dinoTopY;
        state = RUNNING;
    }

    public void jump() {
        dinoTop = dinoTopY;
        topPointReached = false;
        state = JUMPING;
    }

    private class DinoImages {

    }*/

}
