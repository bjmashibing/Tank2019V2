package com.mashibing.tank;

public class Main {
    public static void main(String[] args) {


        TankFrame.INSTANCE.setVisible(true);

        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        for (; ; ) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            TankFrame.INSTANCE.repaint();
        }
    }
}
