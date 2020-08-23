package com.example.russianlowrider;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

//GameThread это поток (thread) управляющий обновлением интерфейса в игре.
public class GameThread extends Thread {
    //private boolean firstTime = true;

    public static boolean gameRunning = true;
    ;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;


    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder) {
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
    }

    public static void setRunning(boolean running) {
        gameRunning = running;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();

        while (gameRunning) {
            Canvas canvas = null;
            try {
                // Get Canvas from Holder and lock it.
                canvas = this.surfaceHolder.lockCanvas();

                // Synchronized
                synchronized (canvas) {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                    //TODO коллизия
                    //this.gameSurface.checkCollision();
                }
            } catch (Exception e) {
                // Do nothing.
            } finally {
                if (canvas != null) {
                    // Unlock Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime();
            // Interval to redraw game
            // (Change nanoseconds to milliseconds)
            long waitTime = (now - startTime) / 1000000;
            if (waitTime < 10) {
                waitTime = 10; // Millisecond.
            }
            System.out.print(" Wait Time=" + waitTime);

            try {
                // Sleep.
                sleep(waitTime);
            } catch (InterruptedException e) {
                System.out.print("Thread interrupted: " + e.getLocalizedMessage());
            }
            startTime = System.nanoTime();
            System.out.print(".");
        }
    }
}