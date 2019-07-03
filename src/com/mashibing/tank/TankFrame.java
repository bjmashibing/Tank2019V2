package com.mashibing.tank;

import com.mashibing.tank.chainofresponsibility.BulletTankCollider;
import com.mashibing.tank.chainofresponsibility.BulletWallCollider;
import com.mashibing.tank.chainofresponsibility.Collider;
import com.mashibing.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TankFrame extends Frame {

    public static final TankFrame INSTANCE = new TankFrame();

    private Player myTank;


    ColliderChain chain = new ColliderChain();

    List<AbstractGameObject> objects;

    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;


    private TankFrame() {
        this.setTitle("tank war");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);

        this.addKeyListener(new TankKeyListener());
        initGameObjects();

    }



    private void initGameObjects() {
        myTank = new Player(100, 100, Dir.R, Group.GOOD);


        objects = new ArrayList<>();

        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));

        for (int i = 0; i < tankCount; i++) {
            this.add(new Tank(100 + 50 * i, 200, Dir.D, Group.BAD));
        }

        this.add(new Wall(300, 200, 400, 50));
    }

    public void add(AbstractGameObject go) {
        objects.add(go);
    }




    @Override
    public void paint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("objects:" + objects.size(), 10, 50);
        /*g.drawString("enemies:" + tanks.size(), 10, 70);
        g.drawString("explodes:" + explodes.size(), 10, 90);*/
        g.setColor(c);

        myTank.paint(g);
        for(int i=0; i<objects.size(); i++) {

            if(!objects.get(i).isLive()) {
                objects.remove(i);
                break;
            }

            AbstractGameObject go1 = objects.get(i);
            for(int j=0; j<objects.size(); j++) {
                AbstractGameObject go2 = objects.get(j);
                chain.collide(go1, go2);
            }

            if(objects.get(i).isLive()) {
                objects.get(i).paint(g);
            }
        }



    }

    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }



    private class TankKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}
