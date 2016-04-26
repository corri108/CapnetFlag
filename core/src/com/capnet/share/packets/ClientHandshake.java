package com.capnet.share.packets;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class ClientHandshake implements IPacket{
    public int id;
    public  ClientHandshake()
    {

    }

    public  ClientHandshake(int id)
    {
        this.id = id;
    }

    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer =ByteBuffer.allocate(ByteHelper.INT);
        buffer.putInt(id);
        return buffer;
    }

    @Override
    public void Decode(ByteBuffer data) {
        id = data.getInt();
    }
}
