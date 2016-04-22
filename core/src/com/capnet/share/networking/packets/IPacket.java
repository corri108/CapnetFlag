package com.capnet.share.networking.packets;

import java.nio.ByteBuffer;

public interface IPacket<E> {
	
	IPacket<?> Instance();
	ByteBuffer Encode();
	void Decode(ByteBuffer data);
	int Id();

}
