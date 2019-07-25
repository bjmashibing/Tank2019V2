package com.mashibing.tank;

import com.mashibing.tank.net.BulletNewMsg;
import com.mashibing.tank.net.Client;

import java.awt.*;
import java.util.UUID;

public class Bullet extends AbstractGameObject {
    public static final int SPEED = 6;
    public static final int W = ResourceMgr.bulletU.getWidth();
    public static final int H = ResourceMgr.bulletU.getHeight();

    private int x, y;
    private Dir dir;
    private UUID id = UUID.randomUUID();
    private UUID playerId;


    public void setId(UUID id) {
        this.id = id;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private Group group;
    private boolean live = true;
    private int w = ResourceMgr.bulletU.getWidth();
    private int h = ResourceMgr.bulletU.getHeight();

    private Rectangle rect;

    public Bullet(UUID playerId, int x, int y, Dir dir, Group group) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rect = new Rectangle(x, y, w, h);


    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void paint(Graphics g) {
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }


        move();

        //update the rect
        rect.x = x;
        rect.y = y;

       /* Color old = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(old);*/
    }

    private void move() {

        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }

        boundsCheck();
    }



    public Rectangle getRect() {
        return rect;
    }

    private void boundsCheck() {
        if (x < 0 || y < 30 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            live = false;
        }
    }

    public void die() {
        this.setLive(false);
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                ", live=" + live +
                ", w=" + w +
                ", h=" + h +
                ", rect=" + rect +
                '}';
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getPlayerId() {
        return this.playerId;
    }
}
