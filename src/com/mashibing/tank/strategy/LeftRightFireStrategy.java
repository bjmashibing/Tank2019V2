package com.mashibing.tank.strategy;

import com.mashibing.tank.*;

public class LeftRightFireStrategy implements FireStrategy {
    @Override
    public void fire(Player p) {
        int bX = p.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bY = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;

        Dir[] dirs = Dir.values();


        TankFrame.INSTANCE.add(new Bullet(bX, bY, Dir.L, p.getGroup()));
        TankFrame.INSTANCE.add(new Bullet(bX, bY, Dir.R, p.getGroup()));
    }
}
