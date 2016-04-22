package com.capnet.share.networking.packets;

import com.badlogic.gdx.math.Vector2;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/21/16.
 */
public  class ByteHelper {
    public static final int INT = 4;
    public static final int SHORT = 2;
    public static final int CHAR = 2;
    public static final int BOOL = 1;
    public static final int FLOAT = 4;

    public void EncodeVector2(ByteBuffer buffer,Vector2 input)
    {
        buffer.putFloat(input.x);
        buffer.putFloat(input.y);
    }

    public Vector2 DecodeVector2(ByteBuffer buffer)
    {
        float x = buffer.getFloat();
        float y = buffer.getFloat();
        return  new Vector2(x,y);
    }


    public static void EncodeString(ByteBuffer buffer, String input)
    {
        buffer.putInt(input.length());
        for(int x = 0; x < input.length(); x++) {
            buffer.putChar(input.charAt(x));
        }
    }

    public  static String DecodeString(ByteBuffer buffer)
    {
        StringBuffer stringBuffer = new StringBuffer();
        int size = buffer.getInt();
        for (int x = 0; x < size; x++) {
            stringBuffer.append(buffer.getChar(x));
        }
        return  stringBuffer.toString();

    }

}
