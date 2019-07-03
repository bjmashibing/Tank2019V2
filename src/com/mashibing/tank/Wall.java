package com.mashibing.tank;

import java.awt.*;

public class Wall extends AbstractGameObject {
    private int x, y, w, h;
    private Rectangle rect;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        rect = new Rectangle(x, y, w, h);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);

        Color old = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(old);
    }

    @Override
    public boolean isLive() {
        return true;
    }

    public Rectangle getRect() {
        return rect;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                ", rect=" + rect +
                '}';
    }
}
