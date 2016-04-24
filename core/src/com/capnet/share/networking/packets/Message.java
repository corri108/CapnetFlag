package com.capnet.share.networking.packets;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/24/16.
 */
public class Message implements IPacket {
    public  String Message;

    public  Message()
    {

    }
    public  Message(String message)
    {
        this.Message = message;
    }
    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer = ByteBuffer.allocate(ByteHelper.SizeOfString(Message));
        ByteHelper.EncodeString(buffer,Message);
        return buffer;
    }

    @Override
    public void Decode(ByteBuffer data) {
        Message = ByteHelper.DecodeString(data);
    }
}
