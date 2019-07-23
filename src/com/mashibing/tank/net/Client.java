package com.mashibing.tank.net;

import com.mashibing.nettychat.ClientFrame;
import com.mashibing.nettycodec.TankMsg;
import com.mashibing.nettycodec.TankMsgEncoder;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {

    public static final Client INSTANCE = new Client();



    private Channel channel = null;

    private Client() {}

    public void connect(){
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;
                    socketChannel.pipeline()
                            .addLast(new MsgEncoder())
                            .addLast(new MsgDecoder())
                            .addLast(new MyHandler());
                }
            });

            ChannelFuture future = b.connect("localhost", 8888).sync();
            System.out.println("connected to server");

            //等待关闭
            future.channel().closeFuture().sync();
            System.out.println("go on");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public void closeConnection() {

        channel.close();
    }

    public void send(Msg msg) {
        channel.writeAndFlush(msg);
    }

    static class MyHandler extends SimpleChannelInboundHandler<Msg> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
        }



        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
            System.out.println(msg);
            msg.handle();
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.connect();
    }
}
