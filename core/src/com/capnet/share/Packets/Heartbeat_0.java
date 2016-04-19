package com.capnet.share.Packets;

public class Heartbeat_0 implements IPacket<Heartbeat_0> {

	@Override
	public Heartbeat_0 Instance() {
		return new Heartbeat_0();
	}

	@Override
	public byte[] Encode() {
		return null;
	}

	@Override
	public void Decode(byte[] data) {
		
	}

	@Override
	public int Id() {
		return 0;
	}



}
