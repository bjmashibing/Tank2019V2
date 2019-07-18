package com.mashibing.tank;

import com.mashibing.tank.net.Client;

public class Main {
    public static void main(String[] args) {


        TankFrame.INSTANCE.setVisible(true);

        new Thread(()->new Audio("audio/war1.wav").loop()).start();


        new Thread(()-> {
            for (; ; ) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TankFrame.INSTANCE.repaint();
            }
        }).start();

        Client.INSTANCE.connect();
    }
}
