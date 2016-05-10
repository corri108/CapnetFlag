package com.capnet.share.packets;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 5/9/16.
 */
public class Ready implements IPacket{
    public  boolean ready = false;
    public  int id;

    public Ready(boolean ready, int id)
    {
        this.ready = ready;
        this.id = id;
    }

    public  Ready()
    {

    }

    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer = ByteBuffer.allocate(1 + ByteHelper.INT);
        buffer.putInt(id);
        buffer.put((byte) (ready == true ? 1 : 0));
        return buffer;
    }

    @Override
    public void Decode(ByteBuffer data) {
        id =  data.getInt();
        ready =   data.get() == 1 ? true : false;

    }
}
