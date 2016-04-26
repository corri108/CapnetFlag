package com.capnet.share.Entities.Packets;

import com.badlogic.gdx.math.Vector2;
import com.capnet.share.Entities.Player;
import com.capnet.share.packets.IPacket;

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
        Id = player.GetPlayerId();
        position = player.Location;
        velocity = player.Location;

    }

    public int PlayerId()
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
