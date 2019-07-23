package com.mashibing.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if(buf.readableBytes() < 8) return;

        buf.markReaderIndex();

        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();

        if(buf.readableBytes() < length) {
            buf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        Msg msg = null;
        msg = (Msg)Class.forName("com.mashibing.tank.net." + msgType.toString() + "Msg")
                .getDeclaredConstructor()
                .newInstance();

        msg.parse(bytes);
        list.add(msg);
    }
}
