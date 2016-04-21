package com.capnet.share.Packets;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class Player_1 implements IPacket<Player_1> {
    private  int Id;

    public int GetId()
    {
        return Id;
    }

    @Override
    public IPacket<?> Instance() {
        return new Player_1();
    }

    @Override
    public byte[] Encode() {
        return new byte[0];
    }

    @Override
    public void Decode(byte[] data) {

    }

    @Override
    public int Id() {
        return 1;
    }
}
