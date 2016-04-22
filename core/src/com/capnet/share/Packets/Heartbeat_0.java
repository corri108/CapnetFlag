package com.capnet.share.Packets;

import java.nio.ByteBuffer;

public class Heartbeat_0 implements IPacket<Heartbeat_0> {


	@Override
	public IPacket<?> Instance() {
		return new Heartbeat_0();
	}

	@Override
	public ByteBuffer Encode() {

		return ByteBuffer.allocate(0);
	}

	@Override
	public void Decode(ByteBuffer data) {

	}

	@Override
	public int Id() {
		return 0;
	}
}
