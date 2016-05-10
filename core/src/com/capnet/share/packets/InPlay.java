package com.capnet.share.packets;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 5/9/16.
 */
public class InPlay implements IPacket{
    public  boolean inplay = false;
    public  int id;

    public InPlay(boolean ready, int id)
    {
        this.inplay = ready;
        this.id = id;
    }

    public  InPlay()
    {

    }

    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer = ByteBuffer.allocate(1 + ByteHelper.INT);
        buffer.putInt(id);
        buffer.put((byte) (inplay == true ? 1 : 0));
        return buffer;
    }

    @Override
    public void Decode(ByteBuffer data) {
        id =  data.getInt();
        inplay =   data.get() == 1 ? true : false;

    }
}
