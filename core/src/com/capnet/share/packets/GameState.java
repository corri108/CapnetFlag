package com.capnet.share.packets;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 5/2/16.
 */
public class GameState implements IPacket {
    public  static  final  int WAITING = 0;
    public  static  final  int IN_PLAY = 1;
    public  static  final  int CLOSING = 2;

    public  int gameState = 0;

    public  GameState()
    {

    }

    public  GameState(int state)
    {
        this.gameState = state;
    }

    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer = ByteBuffer.allocate(ByteHelper.INT);
        buffer.putInt(gameState);
        return  buffer;
    }

    @Override
    public void Decode(ByteBuffer data) {
        gameState = data.getInt();
    }
}
