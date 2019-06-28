package com.mashibing.tank;

import java.util.Random;

public enum Dir {
    //public static final com.mashibing.tank.Dir L;

    L, U, R, D;

    private static Random r = new Random();

    public static Dir randomDir() {
        return values()[r.nextInt(values().length)];
    }
}

//int dir = 1, 2, 3, 4 dir = 5;
