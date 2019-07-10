package com.mashibing.net.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress("localhost", 8888));
        boolean started = true;

        while(started) {
            Socket s = ss.accept();

            new Thread(()->{
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String str = reader.readLine();

                    System.out.println(str);

                    reader.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();


        }

        ss.close();
    }
}
