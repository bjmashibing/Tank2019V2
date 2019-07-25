package com.mashibing.tank;

import com.mashibing.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameModel implements Serializable {
    private Player myTank;


    ColliderChain chain = new ColliderChain();

    List<AbstractGameObject> objects;

    Random r = new Random();

    public GameModel() {
        initGameObjects();
    }

    private void initGameObjects() {


        myTank = new Player(50 + r.nextInt(700), 50 + r.nextInt(500),
                Dir.values()[r.nextInt(Dir.values().length)],
                Group.values()[r.nextInt(Group.values().length)]);


        objects = new ArrayList<>();

        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));

        for (int i = 0; i < tankCount; i++) {
            this.add(new Tank(100 + 80 * i, 200, Dir.D, Group.BAD));
        }

        //this.add(new Wall(300, 200, 400, 50));
    }

    public void add(AbstractGameObject go) {
        objects.add(go);
    }

    public void paint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("objects:" + objects.size(), 10, 50);
        /*g.drawString("enemies:" + tanks.size(), 10, 70);
        g.drawString("explodes:" + explodes.size(), 10, 90);*/
        g.setColor(c);

        myTank.paint(g);

        for(int i=0; i<objects.size(); i++) {
            AbstractGameObject object = objects.get(i);
            if(!object.isLive()) {
                objects.remove(object);
                break;
            }
        }

        for(int i=0; i<objects.size(); i++) {


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

    public Player getMyTank() {
        return myTank;
    }

    public Tank findTankByUUID(UUID id) {
        for(AbstractGameObject o : objects) {
            if(o instanceof Tank) {
                Tank t = (Tank)o;
                if(id.equals(t.getId())) return t;
            }
        }

        return null;
    }

    public Bullet findBulletByUUID(UUID bulletId) {
        for(AbstractGameObject o : objects) {
            if(o instanceof Bullet) {
                Bullet b = (Bullet)o;
                if(bulletId.equals(b.getId())) return b;
            }
        }

        return null;
    }
}
