package com.example.russianlowrider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Класс  GameSurface полностью отрисовывает поверхность игры, этот класс расширен из  SurfaceView,
// SurfaceView содержит объект  Canvas, объекты в игре будут нарисованы на Canvas.
public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    // SFX
    private static final int MAX_STREAMS = 100;
    //Список персонажей на экране
    private final List<ChibiCharacter> chibiList = new ArrayList<>();
    // Список взрывов
    private final List<Explosion> explosionList = new ArrayList<>();

    //private ChibiCharacter chibi1;
    private GameThread gameThread;
    private int soundIdExplosion;
    private int soundIdBackground;

    private boolean soundPoolLoaded;
    private SoundPool soundPool;

//    private boolean mediaPlayerLoaded;
//    private MediaPlayer mediaPlayer;


    public GameSurface(Context context) {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);

        // Инициализация SFX
        this.initSoundPool();
    }

    private void initSoundPool() {
        // With Android API >= 21.
        if (Build.VERSION.SDK_INT >= 21) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // With Android API < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When SoundPool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;

                //mediaPlayerLoaded = true;
                // Playing background sound.

                playSoundBackground();
            }
        });

        // Load the sound background.mp3 into SoundPool = 30 sec or 6 sec max
        this.soundIdBackground = this.soundPool.load(this.getContext(), R.raw.moon_dust, 1);

        // Hotline Background Long MP3
        //this.mediaPlayer = MediaPlayer.create(this.getContext(), R.raw.moon_dust);


        // Load the sound explosion.wav into SoundPool
        this.soundIdExplosion = this.soundPool.load(this.getContext(), R.raw.explosion, 1);


    }

    public void playSoundExplosion() {
        if (this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn = 0.8f;
            // Play sound explosion.wav
            int streamId = this.soundPool.play(this.soundIdExplosion, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSoundBackground() {
        if (this.soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn = 0.8f;
            // Play sound background.mp3
            // Loop -1 = infinite
            int streamId = this.soundPool.play(this.soundIdBackground, leftVolumn, rightVolumn, 1, -1, 1f);
        }

//        if(this.mediaPlayerLoaded){
//            try {
//                this.mediaPlayer.setDataSource("/res/raw/moon_dust.mp3");
//                this.mediaPlayer.prepare();
//                this.mediaPlayer.start();
//            }catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    public void update() {
        //Несколько персонажей
        for (ChibiCharacter chibi : chibiList) {
            chibi.update();
        }

        //Взрывы
        for (Explosion explosion : this.explosionList) {
            explosion.update();
        }

        Iterator<Explosion> iterator = this.explosionList.iterator();
        while (iterator.hasNext()) {
            Explosion explosion = iterator.next();

            // If explosion finish, Remove the current element from the iterator & list.
            //continue;
            if (explosion.isFinish()) iterator.remove();
        }
    }

    /* обрабатывать события при прикосновении пользователя на экран, персонаж игры будет бежать
в направлении нажатия. Вам нужно обработать это событие на классе  GameSurface.*/
    //@SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

// Один персонаж

//            int movingVectorX =x-  this.chibi1.getX() ;
//            int movingVectorY =y-  this.chibi1.getY() ;
//
//            this.chibi1.setMovingVector(movingVectorX,movingVectorY);

            Iterator<ChibiCharacter> iterator = this.chibiList.iterator();

            while (iterator.hasNext()) {
                ChibiCharacter chibi = iterator.next();
                if (chibi.getX() < x && x < chibi.getX() + chibi.getWidth()
                        && chibi.getY() < y && y < chibi.getY() + chibi.getHeight()) {
                    // Remove the current element from the iterator and the list.
                    iterator.remove();

                    // Create Explosion object.
                    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.explosion);
                    Explosion explosion = new Explosion(this, bitmap, chibi.getX(), chibi.getY());

                    this.explosionList.add(explosion);
                }
            }


            // Для всех персонажей на экране
            for (ChibiCharacter chibi : chibiList) {
                int movingVectorX = x - chibi.getX();
                int movingVectorY = y - chibi.getY();
                chibi.setMovingVector(movingVectorX, movingVectorY);
            }

            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //this.chibi1.draw(canvas);

        for (ChibiCharacter chibi : chibiList) {
            chibi.draw(canvas);
        }

        for (Explosion explosion : this.explosionList) {
            explosion.draw(canvas);
        }

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi1);
        ChibiCharacter chibi1 = new ChibiCharacter(this, chibiBitmap1, 100, 50);

        Bitmap chibiBitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi2);
        ChibiCharacter chibi2 = new ChibiCharacter(this, chibiBitmap2, 300, 150);

        this.chibiList.add(chibi1);
        this.chibiList.add(chibi2);

        this.gameThread = new GameThread(this, holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = true;
        }
    }


}
