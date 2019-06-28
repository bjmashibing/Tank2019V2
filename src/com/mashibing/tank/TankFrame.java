package com.mashibing.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {

    public static final TankFrame INSTANCE = new TankFrame();

    private Player myTank;


    private List<Explode> explodes;
    private List<Tank> tanks;
    private List<Bullet> bullets;

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


        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        explodes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            tanks.add(new Tank(100 + 50 * i, 200, Dir.D, Group.BAD));
        }
    }

    public void add(Bullet bullet) {
        this.bullets.add(bullet);
    }

    @Override
    public void paint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullets:" + bullets.size(), 10, 50);
        g.drawString("enemies:" + tanks.size(), 10, 70);
        g.drawString("explodes:" + explodes.size(), 10, 90);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < tanks.size(); i++) {
            if (!tanks.get(i).isLive()) {
                tanks.remove(i);
            } else {
                tanks.get(i).paint(g);
            }
        }

        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collidesWithTank(tanks.get(j));
            }

            if (!bullets.get(i).isLive()) {
                bullets.remove(i);
            } else {
                bullets.get(i).paint(g);
            }
        }

        for (int i = 0; i < explodes.size(); i++) {
            if (!explodes.get(i).isLive()) {
                explodes.remove(i);
            } else {
                explodes.get(i).paint(g);
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

    public void add(Explode explode) {
        this.explodes.add(explode);
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
