package com.mashibing.tank;

import java.awt.*;
import java.io.Serializable;

public abstract class AbstractGameObject implements Serializable {
    public abstract void paint(Graphics g);

    public abstract boolean isLive();
}
