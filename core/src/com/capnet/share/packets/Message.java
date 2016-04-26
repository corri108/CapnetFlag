package com.capnet.share.packets;

import com.capnet.share.Entities.Player;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/24/16.
 */
public class Message implements IPacket {
    public  String Message;
    public  int PlayerId = -1;

    public  Message()
    {

    }
    public  Message(String message)
    {
        this.Message = message;
    }
    public  Message(Player player, String message)
    {
        this.PlayerId = player.GetPlayerId();
        this.Message = message;
    }
    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer = ByteBuffer.allocate(ByteHelper.INT + ByteHelper.SizeOfString(Message));
        buffer.putInt( this.PlayerId);
        ByteHelper.EncodeString(buffer,Message);
        return buffer;
    }

    @Override
    public void Decode(ByteBuffer data) {
        PlayerId = data.getInt();
        Message = ByteHelper.DecodeString(data);
    }
}
