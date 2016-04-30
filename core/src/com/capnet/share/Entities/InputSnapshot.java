package com.capnet.share.Entities;

import com.capnet.share.packets.IPacket;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/29/16.
 */
public class InputSnapshot implements IPacket {
    @Override
    public ByteBuffer Encode() {
        return null;
    }

    @Override
    public void Decode(ByteBuffer data) {

    }
}
