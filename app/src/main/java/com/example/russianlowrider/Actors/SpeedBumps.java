package com.example.russianlowrider.Actors;

import android.graphics.Canvas;
import android.media.Image;
import android.view.SurfaceHolder;

/*import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;*/
import java.util.ArrayList;
import java.util.Iterator;


public class SpeedBumps {
    private SurfaceHolder surfaceHolder;
    //TODO Описание класса лежака
/*
    private class SpeedBump {
        Image image;
        int x, y;
       //Canvas canvas = surfaceHolder.lockCanvas();

        Rectangle getObstacle() {


            Rectangle bump = new Rectangle();
            bump.x = x;
            bump.y = y;
            bump.width = image.getWidth();
            bump.height = image.getHeight();

            return bump;
        }
    }
    private int firstX;
    private int speedBumpInterval;
    private int movementSpeed;

    private ArrayList<BufferedImage> imageList;
    private ArrayList<SpeedBump> sbList;

    private SpeedBump blockedAt;

    public SpeedBump(int firstPos) {
        sbList = new ArrayList<SpeedBump>();
        imageList = new ArrayList<BufferedImage>();

        firstX = firstPos;
        speedBumpInterval = 200;
        movementSpeed = 11;

        imageList.add(new Resource().getResourceImage("../images/Cactus-1.png"));
        imageList.add(new Resource().getResourceImage("../images/Cactus-2.png"));
        imageList.add(new Resource().getResourceImage("../images/Cactus-2.png"));
        imageList.add(new Resource().getResourceImage("../images/Cactus-1.png"));
        // imageList.add(new Resource().getResourceImage("../images/Cactus-3.png"));
        // imageList.add(new Resource().getResourceImage("../images/Cactus-4.png"));
        imageList.add(new Resource().getResourceImage("../images/Cactus-5.png"));

        int x = firstX;

        for(BufferedImage bi : imageList) {

            SpeedBump sb = new SpeedBump();

            sb.image = bi;
            sb.x = x;
            sb.y = Ground.GROUND_Y - bi.getHeight() + 5;
            x += speedBumpInterval;

            sbList.add(sb);
        }
    }

    public void update() {
        Iterator<SpeedBump> looper = sbList.iterator();

        SpeedBump firstOb = looper.next();
        firstOb.x -= movementSpeed;

        while(looper.hasNext()) {
            SpeedBump sb = looper.next();
            sb.x -= movementSpeed;
        }

        SpeedBump lastOb = sbList.get(sbList.size() - 1);

        if(firstOb.x < -firstOb.image.getWidth()) {
            sbList.remove(firstOb);
            firstOb.x = sbList.get(sbList.size() - 1).x + speedBumpInterval;
            sbList.add(firstOb);
        }
    }

    public void create(Graphics g) {
        for(SpeedBump sb : sbList) {
            g.setColor(Color.black);
            // g.drawRect(ob.getObstacle().x, ob.getObstacle().y, ob.getObstacle().width, ob.getObstacle().height);
            g.drawImage(sb.image, sb.x, sb.y, null);
        }
    }

    public boolean hasCollided() {
        for(SpeedBump sb : sbList) {
            if(Dino.getDino().intersects(sb.getObstacle())) {
                System.out.println("Dino = " + Dino.getDino() + "\nObstacle = " + sb.getObstacle() + "\n\n");
                blockedAt = sb;
                return true;
            }
        }
        return false;
    }

    public void resume() {
        // this.obList = null;
        int x = firstX/2;
        sbList = new ArrayList<SpeedBump>();

        for(BufferedImage bi : imageList) {

            SpeedBump sb = new SpeedBump();

            sb.image = bi;
            sb.x = x;
            sb.y = Ground.GROUND_Y - bi.getHeight() + 5;
            x += speedBumpInterval;

            sbList.add(ob);
        }
    }
*/

}
