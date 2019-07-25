package com.mashibing.tank;

import com.mashibing.tank.net.BulletNewMsg;
import com.mashibing.tank.net.Client;
import com.mashibing.tank.net.TankMoveOrDirChangeMsg;
import com.mashibing.tank.net.TankStopMsg;
import com.mashibing.tank.strategy.FireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class Player extends AbstractGameObject {
    public static final int SPEED = 5;
    private int x, y;
    private Dir dir;
    private boolean bL, bU, bR, bD;
    private boolean moving = false;
    private Group group;
    private boolean live = true;
    private UUID id = UUID.randomUUID();
    private FireStrategy strategy = null;

    public Player(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        //init fire strategy from config file
        this.initFireStrategy();
    }

    public UUID getId() {
        return id;
    }

    public boolean isMoving() {
        return moving;
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

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
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

    public void paint(Graphics g) {

        if (!this.isLive()) return;


        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.drawString(id.toString(), x, y-10);
        g.setColor(c);


        switch (dir) {
            case L:
                g.drawImage(this.group.equals(Group.BAD)? ResourceMgr.badTankL:ResourceMgr.goodTankL, x, y, null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD)? ResourceMgr.badTankU:ResourceMgr.goodTankU, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD)? ResourceMgr.badTankR:ResourceMgr.goodTankR, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD)? ResourceMgr.badTankD:ResourceMgr.goodTankD, x, y, null);
                break;
        }


        move();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }

        setMainDir();

    }

    private void setMainDir() {

        boolean oldMoving = moving;
        Dir oldDir = this.getDir();

        //all dir keys are released , tank should be stop.
        if (!bL && !bU && !bR && !bD) {
            moving = false;
            //send stop msg
            Client.INSTANCE.send(new TankStopMsg(this.id, this.x, this.y));
        }
            //any dir key is pressed, tank should be moving.
        else {
            moving = true;

            if (bL && !bU && !bR && !bD)
                dir = Dir.L;
            if (!bL && bU && !bR && !bD)
                dir = Dir.U;
            if (!bL && !bU && bR && !bD)
                dir = Dir.R;
            if (!bL && !bU && !bR && bD)
                dir = Dir.D;

            //old status is not moving , now my tank will move immediate
            if(!oldMoving)
                Client.INSTANCE.send(new TankMoveOrDirChangeMsg(this.id, this.x, this.y, this.dir));
            if(!this.dir.equals(oldDir))
                Client.INSTANCE.send(new TankMoveOrDirChangeMsg(this.id, this.x, this.y, this.dir));
        }
    }

    private void move() {
        if (!moving) return;

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
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }

        setMainDir();
    }

    private void initFireStrategy() {

        String className = PropertyMgr.get("tankFireStrategy");
        try {
            Class clazz = Class.forName("com.mashibing.tank.strategy." + className);
            strategy = (FireStrategy) (clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fire() {
        //read config
        //if default four dir two
        //ClassLoader loader = Player.class.getClassLoader();


        strategy.fire(this);


    }

    public void die() {
        this.setLive(false);
        TankFrame.INSTANCE.getGm().add(new Explode(x, y));
    }
}

//mashibing.com -> 菜鸟预习 -> 2019年面向对象 OO的核心