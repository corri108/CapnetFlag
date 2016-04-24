package com.capnet.share;

import com.badlogic.gdx.math.Vector2;
import com.capnet.share.networking.packets.IPacket;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class Player implements IPacket{
    public Vector2 Location= new Vector2();
    public Vector2 Velocity = new Vector2();


    @Override
    public ByteBuffer Encode() {
        return null;
    }

    @Override
    public void Decode(ByteBuffer data) {

    }

}
