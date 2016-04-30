package com.capnet.share.Entities;

import com.capnet.share.packets.ByteHelper;
import com.capnet.share.packets.IPacket;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/29/16.
 */
public class InputSnapshot implements IPacket {
    private  int key = 0;
    private  boolean isPressed = false;

    public int GetKey() {
        return  key;
    }

    public  boolean IsPressed(){
        return isPressed;
    }

    public InputSnapshot() {
    }

    public InputSnapshot(int key, boolean isPressed) {
        this.key = key;
        this.isPressed = isPressed;

    }
    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer = ByteBuffer.allocate(ByteHelper.INT+ ByteHelper.CHAR);
        buffer.putInt(key);
        buffer.put((byte) (isPressed ? 1 : 0));
        return  buffer;
    }

    @Override
    public void Decode(ByteBuffer data) {
        key = data.getInt();
        isPressed = data.get() == 1;

    }
}
