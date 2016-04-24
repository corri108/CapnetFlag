package com.capnet.share.networking.packets;

import java.nio.ByteBuffer;


public class Heartbeat implements IPacket {


	@Override
	public ByteBuffer Encode() {

		return ByteBuffer.allocate(0);
	}

	@Override
	public void Decode(ByteBuffer data) {

	}
}
