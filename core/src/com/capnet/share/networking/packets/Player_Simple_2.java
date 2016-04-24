package com.capnet.share.networking.packets;

import com.badlogic.gdx.math.Vector2;
import com.capnet.share.Player;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/21/16.
 */

public class Player_Simple_2 implements IPacket {
    private  int Id;
    public Vector2 _position;
    public Vector2 _velocity;


    public Player_Simple_2()
    {

    }

    public Player_Simple_2(Player player)
    {

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
