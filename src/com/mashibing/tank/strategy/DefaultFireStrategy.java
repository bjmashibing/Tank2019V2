package com.mashibing.tank.strategy;

import com.mashibing.tank.*;
import com.mashibing.tank.net.BulletNewMsg;
import com.mashibing.tank.net.Client;

public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Player p) {
        int bX = p.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
//
//        Dir[] dirs = Dir.values();
//
//        for (Dir d : dirs)
        Bullet b = new Bullet(p.getId(), bX, bY, p.getDir(), p.getGroup());
        TankFrame.INSTANCE.getGm().add(b);

        //send a bullet msg to server when a bullet is born.
        Client.INSTANCE.send(new BulletNewMsg(b));


    }
}
