package com.capnet.share.Entities.Packets;

import com.badlogic.gdx.math.Vector2;
import com.capnet.share.Entities.Player;
import com.capnet.share.packets.ByteHelper;
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
        velocity = player.Velocity;

    }

    public int PlayerId()
    {
        return Id;
    }

    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer = ByteBuffer.allocate(ByteHelper.VECTOR2 + ByteHelper.VECTOR2 + ByteHelper.INT);
        ByteHelper.EncodeVector2(buffer,this.position);
        ByteHelper.EncodeVector2(buffer,this.velocity);
        buffer.putInt(Id);
        return buffer;
    }

    @Override
    public void Decode(ByteBuffer data) {
        position = ByteHelper.DecodeVector2(data);
        velocity = ByteHelper.DecodeVector2(data);
        Id = data.getInt();


    }

}
