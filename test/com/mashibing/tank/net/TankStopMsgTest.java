package com.mashibing.tank.net;

import com.mashibing.tank.Dir;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TankStopMsgTest {

    @Test
    void encode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());

        TankStopMsg msg = new TankStopMsg(UUID.randomUUID(), 50, 100);

        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();

        UUID id = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();


        assertEquals(MsgType.TankStop, msgType);
        assertEquals(24, length);

        assertEquals(50, x);
        assertEquals(100, y);

    }

    @Test
    void decode() {
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgDecoder());

        UUID id = UUID.randomUUID();

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStop.ordinal());
        buf.writeInt(24);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(5);
        buf.writeInt(8);


        ch.writeInbound(buf);

        TankStopMsg msg = ch.readInbound();

        assertEquals(id, msg.getId());
        assertEquals(5, msg.getX());
        assertEquals(8, msg.getY());
    }
}