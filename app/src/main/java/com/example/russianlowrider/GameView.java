package com.example.russianlowrider;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;


class GameEvents {
    public static void update() {
        System.out.println("First");
    }
}

//Класс расширяет вью на поверхность
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;

    //Параметры экрана
    private int screenWidth, screenHeight, newWidth, newHeight;
    // Позиция элементов фона и декораций
    private int cloudX = 0, towerX = 0, asphaltX = 0, bgGameX = 0;
    // Расположение асфальта
    private int groundPosition;
    // Картинки для анимации фона, авто
    private Bitmap bgGame, clouds, car;
    private int resizedCarWidth = 600, resizedCarHeight = 196;
    //Цвет асфальта
    private static final int colorAsphalt = Color.parseColor("#2A2922");
    private Paint aspPaint = new Paint();
    private boolean isLeftPneumaPressed, isRightPneumaPressed;

    //Очки
    int score = 0;


    public GameView(Context context) {
        super(context);

        // Initialize
        init();
    }

    public GameView(Context context, AttributeSet a) {
        super(context, a);

        // Initialize
        init();
    }

    public GameView(Context context, AttributeSet a, int b) {
        super(context, a, b);

        // Initialize
        init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void performClick(OnClickListener onClickListener) {
    }

    private void init() {
        //Задаем вид
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);

        //Грузим фон, элементы
        //bgGame = BitmapFactory.decodeResource(getResources(), R.drawable.bg_streets_of_rage);

        //tower = BitmapFactory.decodeResource(getResources(), R.drawable.towers);

        clouds = BitmapFactory.decodeResource(getResources(), R.drawable.clouds);
        // For the волга
        car = BitmapFactory.decodeResource(getResources(), R.drawable.deloan_red_01);

        //asphalt = BitmapFactory.decodeResource(getResources(), R.drawable.asphalt);
/*        car[0] = BitmapFactory.decodeResource(getResources(), R.drawable.car0);
        car[1] = BitmapFactory.decodeResource(getResources(), R.drawable.car1);*/

        //Получаем параметры дисплея
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //Вычисляем позицию асфальта
        groundPosition = screenHeight - screenHeight / 4;

        //Scale Images
        float height = clouds.getHeight();
        float width = clouds.getWidth();
        float ratio = width / height;
        newHeight = screenHeight;
        newWidth = (int) (ratio * screenHeight);

        //Ресайзим
        //bgGame = Bitmap.createScaledBitmap(bgGame, newWidth, newHeight, false);
        clouds = Bitmap.createScaledBitmap(clouds, newWidth, newHeight, false);
//        tower = Bitmap.createScaledBitmap(tower, newWidth, newHeight, false);
//        asphalt = Bitmap.createScaledBitmap(asphalt, newWidth, newHeight, false);
//
        //Красим асфальт
        aspPaint.setColor(colorAsphalt);

        //100 100 false
        car = Bitmap.createScaledBitmap(car, resizedCarWidth, resizedCarHeight, false);

        //TODO Экран всегда включен?
        setKeepScreenOn(true);
    }


    public boolean isCanDrive() {
        //TODO условие проигрыша
        return true;
    }

    public void update() {
        Canvas canvas = surfaceHolder.lockCanvas();

        Animate abg = new Animate(canvas);
        //getHandler().post(abg);
        abg.run();

        // Рисуем асфальт
        // рисуем прямоугольник
        // левая верхняя точка (200,150), нижняя правая (400,200)
        canvas.drawRect(0, groundPosition, screenWidth, screenHeight, aspPaint);

        //Рисуем авто, анимируем в зависимости от нажатия на кнопки
        if (isRightPneumaPressed) {
            //Анимируем если передняя ось поднимается

            isRightPneumaPressed = false;
        }
        if (isLeftPneumaPressed) {
            //Анимируем если задняя ось поднимается

            isLeftPneumaPressed = false;
        }

        canvas.drawBitmap(car, screenWidth / 4.0f - resizedCarWidth / 2.0f, groundPosition - resizedCarHeight, null);

        //Отрисовать кнопки поверх всего
        surfaceHolder.unlockCanvasAndPost(canvas);

    }

    //Обрабатываем нажатие на левую часть экрана - поднимаем (анимируем) подвеску сзади
    public void LeftPneumaPressed() {
        //TODO анимация подъема задней оси
        //car=;
        isLeftPneumaPressed = true;
    }

    //Обрабатываем нажатие на левую часть экрана - поднимаем (анимируем) подвеску передней оси
    public void RightPneumaPressed() {
        //TODO анимация подъема передней оси
        //car=;
        isRightPneumaPressed = true;
        //canvas.drawBitmap(car, screenWidth / 4.0f - resizedCarWidth / 2.0f, groundPosition - resizedCarHeight, null);
    }


    public class Animate implements Runnable {
        private Canvas canvas;


        public Animate(Canvas _canvas) {
            this.canvas = _canvas;
        }

        public void run() {
            // Clear the canvas

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);


            //TODO Переделать "склейку фона"

            // decrement the far background
            bgGameX -= 1;
            cloudX -= 2; //Чем больше число тем быстрее
//
//            int newFarX = bgGame.getWidth() - (-bgGameX);
//
//            // if we have scrolled all the way, reset to start
//            if (newFarX <= 0) {
//                bgGameX = 0;
//                cloudX = 0;
//                // only need one draw
//                canvas.drawBitmap(bgGame, bgGameX, 0, null);
//                canvas.drawBitmap(clouds, cloudX, 0, null);
//            } else {
//                // need to draw original and wrap
//                canvas.drawBitmap(bgGame, bgGameX, 0, null);
//                canvas.drawBitmap(bgGame, newFarX, 0, null);
//
//                canvas.drawBitmap(clouds, cloudX, 0, null);
//                canvas.drawBitmap(clouds, newFarX, 0, null);
//            }
//

/*
            if (bgGameX < -newWidth) {
                bgGameX = 0;
            }
            canvas.drawBitmap(bgGame, bgGameX, 0, null);
            if (bgGameX < screenWidth - newWidth) {
                canvas.drawBitmap(bgGame, bgGameX + newWidth, 0, null);
            }
*/


//            if (cloudX < -newWidth) {
//                cloudX = 0;
//            }
//            canvas.drawBitmap(clouds, cloudX, 0, null);
//            if (cloudX < screenWidth - newWidth) {
//                canvas.drawBitmap(clouds, cloudX + newWidth, 0, null);
//            }


        }
    }

    public int getScore() {
        //TODO начисление очков за прохождение лежаков

        return score;
    }

    public void resetData() {
        //TODO чистим переменные
        score = 0;
    }


}
