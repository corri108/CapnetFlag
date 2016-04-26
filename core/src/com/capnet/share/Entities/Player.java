package com.capnet.share.Entities;

import com.badlogic.gdx.math.Vector2;
import com.capnet.share.networking.packets.ByteHelper;
import com.capnet.share.networking.packets.IPacket;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/21/16.
 */
public class Player implements IPacket{
    public Vector2 Location= new Vector2();
    public Vector2 Velocity = new Vector2();
    

    public  int id = 10; //used for the server to communicate with players
    public  String name = "hello this is  a test";

    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer = ByteBuffer.allocate(ByteHelper.SizeOfString(name)+ByteHelper.INT + ByteHelper.VECTOR2 + ByteHelper.VECTOR2 );
        buffer.putInt(id);
        ByteHelper.EncodeString(buffer,name);
        ByteHelper.EncodeVector2(buffer,Location);
        ByteHelper.EncodeVector2(buffer,Velocity);
        return  buffer;

    }

    @Override
    public void Decode(ByteBuffer data) {
        id = data.getInt();
        name = ByteHelper.DecodeString(data);
        Location = ByteHelper.DecodeVector2(data);
        Velocity = ByteHelper.DecodeVector2(data);

    }

}
