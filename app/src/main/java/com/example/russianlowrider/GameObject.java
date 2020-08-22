package com.example.russianlowrider;

import android.graphics.Bitmap;

public abstract class GameObject {
    protected final int rowCount;
    protected final int colCount;
    protected final int WIDTH;
    protected final int HEIGHT;
    protected final int width;
    protected final int height;
    //Это шаблон для персонажа, если BMP разделен на 12 секторов
    protected Bitmap image;
    protected int x;
    protected int y;

    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y) {

        this.image = image;
        this.rowCount = rowCount;
        this.colCount = colCount;

        this.x = x;
        this.y = y;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = this.WIDTH / colCount;
        this.height = this.HEIGHT / rowCount;
    }


    protected Bitmap createSubImageAt(int row, int col) {
        // createBitmap(bitmap, x, y, width, height).
        return Bitmap.createBitmap(image, col * width, row * height, width, height);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
