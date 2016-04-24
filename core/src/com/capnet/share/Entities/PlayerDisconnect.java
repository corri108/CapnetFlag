package com.capnet.share.Entities;

import com.capnet.share.networking.packets.IPacket;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/23/16.
 */
public class PlayerDisconnect implements IPacket {
    @Override
    public ByteBuffer Encode() {
        return null;
    }

    @Override
    public void Decode(ByteBuffer data) {

    }
}
