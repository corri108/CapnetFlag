package com.capnet.share.Entities;

import com.badlogic.gdx.math.Vector2;
import com.capnet.share.networking.packets.IPacket;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/21/16.
 */

public class PlayerSimple implements IPacket {
    private  int Id;
    public Vector2 position;
    public Vector2 velocity;


    public PlayerSimple()
    {

    }

    public PlayerSimple(Player player)
    {
        Id = player.id;
        position = player.Location;
        velocity = player.Location;

    }

    public int GetId()
    {
        return Id;
    }

    @Override
    public ByteBuffer Encode() {
        return ByteBuffer.allocate(0);
    }

    @Override
    public void Decode(ByteBuffer data) {

    }

}
